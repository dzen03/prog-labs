package com.dzen03.run;

import com.dzen03.core.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
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
    public static final int BUFFER_LENGTH = 65535 - 28 - 2 * BYTENUMPACKET; // max UDP packet size - ipv4 data - number of packet *2

    public final static Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void server(String[] args)
    {
        System.out.print(ANSI_RESET);

        if (args.length == 0)
        {
            System.out.println(ANSI_RED + "You need to specify file!" + ANSI_RESET);
            return;
        }


        FileInputStream file;
        try
        {
            file = new FileInputStream(args[0]);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            return;
        }

        InputStreamReader inputStream = new InputStreamReader(file);
        StringBuilder inputData = new StringBuilder();

        try
        {
            int data = inputStream.read();

            while (data != -1)
            {
                inputData.append((char) data);
                data = inputStream.read();
            }

            inputStream.close();
            file.close();
        }
        catch (IOException ex)
        {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            return;
        }

        String[] packedCities = inputData.toString().split("\n");

        for (String packedCity: packedCities)
        {
            try
            {
                Data.citiesVector.add(new City(packedCity.split(",")));
            }
            catch (IncorrectFieldException | NumberFormatException | ArrayIndexOutOfBoundsException ex)
            {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
//                ex.printStackTrace();
                return;
            }
        }

        System.out.print(ANSI_GREEN);



        try
        {

            Selector selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(PORT));
            channel.register(selector, SelectionKey.OP_READ);

//            System.out.println(1);



            // TODO datagram init
            while (true)
            {
                int readyChannels = selector.select();
//                LOGGER.log(Level.INFO, "Selected Keys on : " + selector.selectedKeys() + " with keys count : " + selector.selectedKeys().size());
                if (readyChannels == 0)
                {
                    continue;
                }

//                System.out.println("~~~");
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();


                while (keyIterator.hasNext())
                {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
//                    System.out.println(123);
                    if (!key.isReadable())
                        continue;

                    DatagramChannel readChannel = (DatagramChannel) key.channel();

                    HashMap<Integer, ArrayList<Byte>> bytes = new HashMap<>();

                    int count = Integer.MAX_VALUE;
                    SocketAddress address = null;
                    for (int i = 0; i < count; ++i)
                    {
                        ByteBuffer buf = ByteBuffer.allocate(BUFFER_LENGTH);
                        address = readChannel.receive(buf);
                        buf.flip();
                        int le = buf.remaining();
                        if (le > 0)
                        { // avoid UnderflowException
                            byte[] bb = new byte[le];
                            buf.get(bb);
                        }

                        Object[] tmp = byteArrToData(buf.array());
                        int ind = (int) tmp[0];
                        count = (int) tmp[1];
                        ArrayList<Byte> dt = (ArrayList<Byte>) tmp[2];

                        bytes.put(ind, dt);

                        LOGGER.log(Level.INFO, String.format("%d %d %s", ind, count, bytes));
                    }
                    ArrayList<Byte> value = new ArrayList<>();
                    for(int i = 0; i < count; ++i)
                    {
                        value.addAll(bytes.get(i));
                    }

                    byte[] result = new byte[value.size()];
                    for (int i = 0; i < value.size(); ++i)
                        result[i] = value.get(i);
                    LOGGER.log(Level.INFO, value.toString());
//                    readChannel.configureBlocking(false);



                    Collections.sort(Data.citiesVector);

//            String[] line = in.nextLine().split(" ");


                    try
                    {
//                    CommandClass commandClass
                        CommandClass commandClass;
                        ByteArrayInputStream bis = new ByteArrayInputStream(result);
                        ObjectInput in = new ObjectInputStream(bis);
                        commandClass = (CommandClass) in.readObject();
//                        System.out.println(commandClass);
                        String resp = commandClass.type.execute(commandClass.args);
//                    channel.write(ByteBuffer.wrap(resp.getBytes()));
                        byte[][] data = dataToArrays(resp.getBytes());

                        for (byte[] i : data)
                        {
                            readChannel.send(ByteBuffer.wrap(i), address);
                        }
//                        System.out.println(321);

//                        break;
                    } catch (ShutdownException ex)
                    {
                        System.out.println(ANSI_RED + "Shutting down" + ANSI_RESET);
                        break;
                    } catch (IncorrectCommandException | IncorrectFieldException | StreamCorruptedException ex)
                    {
                        byte[][] data = dataToArrays(ex.getMessage().getBytes());

                        for (byte[] i : data)
                        {
                            readChannel.send(ByteBuffer.wrap(i), address);
                        }
//                        readChannel.send(ByteBuffer.wrap(ex.getMessage().getBytes()), address);
//                        System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
                        continue;
                    } finally
                    {
//                        System.out.println("<<<<");
//                        readChannel.close();
                        System.out.print(ANSI_GREEN);
                    }
                }
//                selector.selectedKeys().clear();
                System.out.print(ANSI_RESET);
            }
        }
        catch (Exception ex)
        {
//            System.out.println(ex);
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }

    }

    public static byte[][] dataToArrays(byte[] data)
    {
        int count = (int) Math.ceil(data.length * 1.0 / BUFFER_LENGTH) + 1;
        ArrayList<Byte>[] tmp = new ArrayList[count];

        for (int offset = 0, length = data.length, ind = 0; ind < count; offset += BUFFER_LENGTH - 2*BYTENUMPACKET, length -= (BUFFER_LENGTH - 2*BYTENUMPACKET), ind += 1)
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

            LOGGER.log(Level.INFO, tmp[ind].toString());
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
        int index = (int)( // NOTE: type cast not necessary for int
                (0xff & in[0]) << 24  |
                (0xff & in[1]) << 16  |
                (0xff & in[2]) << 8   |
                (0xff & in[3]) << 0
        );
        int cnt = (int)( // NOTE: type cast not necessary for int
                (0xff & in[4]) << 24  |
                (0xff & in[5]) << 16  |
                (0xff & in[6]) << 8   |
                (0xff & in[7]) << 0
        );
        ArrayList<Byte> data = new ArrayList<>();
        for (int i = 8; i < in.length; ++i)
            data.add(in[i]);
        return new Object[]{index, cnt, data};
    }


    public static void client() throws Exception
    {
        Scanner in = new Scanner(System.in);

        String address = in.nextLine();
//        System.out.println(Arrays.toString(address));


        InetSocketAddress serverAddr = new InetSocketAddress(InetAddress.getByName(address), PORT);
//        InetSocketAddress clientAddr = new InetSocketAddress(
//                InetAddress.getByName(address), CLIENT_PORT);


        //TODO client datagram

//        ServerSocket serverSocket = new ServerSocket(address, SERVER_PORT);
        DatagramSocket clientSocket = new DatagramSocket();
//        clientSocket.configureBlocking(false);

        System.out.print(ANSI_GREEN);

        while (in.hasNextLine())
        {

            Collections.sort(Data.citiesVector);

//            String[] line = in.nextLine().split(" ");
            ArrayList<String> line = new ArrayList<>(Arrays.asList(in.nextLine().split(" ")));

            System.out.print(ANSI_RESET);

            if (line.size() == 0 || line.get(0).equals(""))
                continue;

            try
            {
                CommandType commandType = CommandType.pick(line.get(0));
                Command command = new CommandFactory().createCommand(commandType);

                String[] args = command.preExecute(line.toArray(new String[0]));
                LOGGER.log(Level.INFO, Arrays.toString(args));
                CommandClass commandClass = new CommandClass(command, args);

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


//                System.out.println(commandClass);
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
                for(int i = 0; i < count; ++i)
                {
                     value.addAll(bytes.get(i));
                }

                byte[] result = new byte[value.size()];
                for (int i = 0; i < value.size(); ++i)
                    result[i] = value.get(i);


                String output = new String(result);

                // TODO write to server datagram

                if (commandType == CommandType.EXIT)
                {
                    throw new ShutdownException();
//                    line.add(args[0]); TODO adapt
                }
                System.out.println(ANSI_YELLOW + output + ANSI_RESET);
            }
            catch (ShutdownException ex)
            {
                System.out.println(ANSI_RED + "Shutting down" + ANSI_RESET);
                break;
            }
            catch (IncorrectCommandException | IncorrectFieldException ex)
            {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
                continue;
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
        if (args.length > 0)
        {
            System.out.println("server");
            server(args);
        }
        else
        {
            try
            {
                System.out.println("client");
                client();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                LOGGER.log(Level.SEVERE, ex.getMessage());
//                System.out.println(ex);
            }
        }
    }



}
