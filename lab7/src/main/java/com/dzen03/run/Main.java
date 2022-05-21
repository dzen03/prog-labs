package com.dzen03.run;

import com.dzen03.core.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final int PORT = 10000;

    public static final int BYTENUMPACKET = 4; // 2^(4*8) = 2^32 = int -- max count of packets, should be enough
    public static int BUFFER_LENGTH = 65535 - 28 - 2 * BYTENUMPACKET; // max UDP packet size - ipv4 data - number of packet *2

    public final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    static ArrayList<ExecuteProcessor> works = new ArrayList<>(); // channel, address, data


    public static ForkJoinPool pool = new ForkJoinPool();

    public static void server(String[] args) throws Exception
    {
        if (args.length > 1)
            BUFFER_LENGTH = Integer.parseInt(args[2]);

        System.out.print(ANSI_RESET);

        Database.connect();

        Data.refresh();

        System.out.print(ANSI_GREEN);


        try
        {

            Selector selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(PORT));
            channel.register(selector, SelectionKey.OP_READ);


            while (true)
            {
                int readyChannels = selector.select();
                if (readyChannels == 0)
                {
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();


                while (keyIterator.hasNext())
                {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (!key.isReadable())
                        continue;

                    DatagramChannel readChannel = (DatagramChannel) key.channel();

                    HashMap<Integer, ArrayList<Byte>> bytes = new HashMap<>();

                    final int[] count = {Integer.MAX_VALUE};
                    final SocketAddress[] address = {null};


                    for (int i = 0; i < count[0]; ++i)
                    {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                ByteBuffer buf = ByteBuffer.allocate(BUFFER_LENGTH);
                                try
                                {
                                    if (address[0] == null)
                                        address[0] = readChannel.receive(buf);
                                    else
                                        readChannel.receive(buf);
                                }
                                catch (Exception ex)
                                {
                                    throw new RuntimeException(ex);
                                }
                                buf.flip();
                                int le = buf.remaining();
                                if (le > 0)
                                { // avoid UnderflowException
                                    byte[] bb = new byte[le];
                                    buf.get(bb);
                                }

                                Object[] tmp = byteArrToData(buf.array());
                                int ind = (int) tmp[0];

                                if (count[0] == Integer.MAX_VALUE)
                                    count[0] = (int) tmp[1];
                                ArrayList<Byte> dt = (ArrayList<Byte>) tmp[2];

                                bytes.put(ind, dt);
                            }
                        };

                        thread.start();
                        if (i == 0)
                            thread.join();


                    }
                    while (bytes.size() != count[0]) ;
                    ArrayList<Byte> value = new ArrayList<>();
                    for (int i = 0; i < count[0]; ++i)
                    {
                        value.addAll(bytes.get(i));
                    }

                    byte[] result = new byte[value.size()];
                    for (int i = 0; i < value.size(); ++i)
                        result[i] = value.get(i);


                    Data.sort();


                    try
                    {
                        CommandClass commandClass;
                        ByteArrayInputStream bis = new ByteArrayInputStream(result);
                        ObjectInput in = new ObjectInputStream(bis);
                        commandClass = (CommandClass) in.readObject();
                        int id = Database.auth(commandClass.login, commandClass.pass);
                        LOGGER.log(Level.INFO, String.format("id %d made request", id));
                        String[] arg = new String[commandClass.args.length + 1];
                        for (int i = 0; i < commandClass.args.length; ++i)
                            arg[i] = commandClass.args[i];
                        arg[arg.length - 1] = String.valueOf(id);

                        ExecuteProcessor executeProcessor = new ExecuteProcessor(commandClass.type, arg, readChannel, address[0]);

                        pool.execute(executeProcessor);
                    }
                    catch (ShutdownException ex)
                    {
                        System.out.println(ANSI_RED + "Shutting down" + ANSI_RESET);
                        break;
                    }
                    catch (IncorrectCommandException | IncorrectFieldException | StreamCorruptedException |
                           AuthException ex)
                    {
                        byte[][] data = dataToArrays(ex.getMessage().getBytes());

                        pool.execute(new SendProcessor(data, 0, data.length, readChannel, address[0]));
                        continue;
                    }
                    finally
                    {
                        System.out.print(ANSI_GREEN);
                    }
                }
                System.out.print(ANSI_RESET);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }

    }

    public static byte[][] dataToArrays(byte[] data)
    {
        int count = (int) Math.ceil(data.length * 1.0 / BUFFER_LENGTH) + 1;
        ArrayList<Byte>[] tmp = new ArrayList[count];

        for (int offset = 0, length = data.length, ind = 0; ind < count; offset += BUFFER_LENGTH - 2 * BYTENUMPACKET,
                length -= (BUFFER_LENGTH - 2 * BYTENUMPACKET), ind += 1)
        {
            tmp[ind] = new ArrayList<>(List.of(new Byte[]{
                    (byte) ((ind >> 24) & 0xff),
                    (byte) ((ind >> 16) & 0xff),
                    (byte) ((ind >> 8) & 0xff),
                    (byte) ((ind >> 0) & 0xff),

                    (byte) ((count >> 24) & 0xff),
                    (byte) ((count >> 16) & 0xff),
                    (byte) ((count >> 8) & 0xff),
                    (byte) ((count >> 0) & 0xff)
            }));

            for (int i = 0; i < Math.min(length, BUFFER_LENGTH); ++i)
            {
                tmp[ind].add(data[offset + i]);
            }
        }
        byte[][] res = new byte[tmp.length][];
        for (int i = 0; i < tmp.length; ++i)
        {
            res[i] = new byte[tmp[i].size()];
            for (int j = 0; j < tmp[i].size(); ++j)
                res[i][j] = tmp[i].get(j);
        }


        return res;
    }

    public static Object[] byteArrToData(byte[] in) // Tuple of int packet_num, int packet_cnt and ArrayList<Byte> value
    {
        byte[] tmp = in.clone();
        // NOTE: type cast not necessary for int
        int index = (0xff & in[0]) << 24 |
                (0xff & in[1]) << 16 |
                (0xff & in[2]) << 8 |
                (0xff & in[3]) << 0;
        // NOTE: type cast not necessary for int
        int cnt = (0xff & in[4]) << 24 |
                (0xff & in[5]) << 16 |
                (0xff & in[6]) << 8 |
                (0xff & in[7]) << 0;
        ArrayList<Byte> data = new ArrayList<>();
        for (int i = 8; i < in.length; ++i)
            data.add(in[i]);
        return new Object[]{index, cnt, data};
    }

    public static String getHash(String input)
    {
        try
        {
            return org.apache.commons.codec.digest.DigestUtils.sha384Hex(input);
        }
        catch (Exception ex)
        {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }


    public static void client() throws Exception
    {
        System.out.println("login:password@adress");
        Scanner in = new Scanner(System.in);

        String[] input = in.nextLine().split("[:@]");
        if (input.length != 3)
            throw new AuthException("incorrect login:password@adress");
        String address = input[2];
        String login = input[0];
        String pass = getHash(input[1]);


        InetSocketAddress serverAddr = new InetSocketAddress(InetAddress.getByName(address), PORT);

        DatagramSocket clientSocket = new DatagramSocket();

        System.out.print(ANSI_GREEN);

        while (in.hasNextLine())
        {
            ArrayList<String> line = new ArrayList<>(Arrays.asList(in.nextLine().split(" ")));

            System.out.print(ANSI_RESET);

            if (line.size() == 0 || line.get(0).equals(""))
                continue;

            try
            {
                CommandType commandType = CommandType.pick(line.get(0));
                Command command = new CommandFactory().createCommand(commandType);

                String[] args = command.preExecute(line.toArray(new String[0]));
                CommandClass commandClass = new CommandClass(command, args, login, pass);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(commandClass);
                byte[] outByte = bos.toByteArray();
                out.close();
                bos.close();

                byte[][] data = dataToArrays(outByte);

                for (byte[] i : data)
                {
                    DatagramPacket outPacket = new DatagramPacket(i, i.length, serverAddr);

                    clientSocket.send(outPacket);
                }

                HashMap<Integer, ArrayList<Byte>> bytes = new HashMap<>();

                int count = Integer.MAX_VALUE;
                for (int i = 0; i < count; ++i)
                {
                    byte[] inByte = new byte[BUFFER_LENGTH];
                    DatagramPacket inPacket = new DatagramPacket(inByte, inByte.length);
                    clientSocket.receive(inPacket);

                    Object[] tmp = byteArrToData(inByte);
                    int ind = (int) tmp[0];
                    count = (int) tmp[1];
                    ArrayList<Byte> dt = (ArrayList<Byte>) tmp[2];

                    bytes.put(ind, dt);
                }
                ArrayList<Byte> value = new ArrayList<>();
                for (int i = 0; i < count; ++i)
                {
                    value.addAll(bytes.get(i));
                }

                byte[] result = new byte[value.size()];
                for (int i = 0; i < value.size(); ++i)
                    result[i] = value.get(i);


                String output = new String(result);


                if (commandType == CommandType.EXIT)
                {
                    throw new ShutdownException();
                }
                System.out.println(ANSI_YELLOW + output + ANSI_RESET);
            }
            catch (ShutdownException ex)
            {
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            }
            finally
            {
                System.out.print(ANSI_GREEN);
            }

        }
        System.out.print(ANSI_RESET);
    }

    public static void main(String[] args)
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {

                    Main.pool.shutdown();
                    Database.exit();
                }
                catch (Exception ex)
                {
                }
                System.out.println(ANSI_RED + "Shutting down" + ANSI_RESET);
                System.out.flush();
            }
        });

        if (args.length > 0 && args[0].equals("-s"))
        {
            try
            {
                System.out.println("server");
                server(args);
            }
            catch (Exception ex)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }
        else if (args.length == 0 || args[0].equals("-c"))
        {
            try
            {
                System.out.println("client");
                client();
            }
            catch (Exception ex)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }
        else
        {
            System.out.println("select what mode you want to use ('-s': server or '-c':client)");
        }
    }
}
