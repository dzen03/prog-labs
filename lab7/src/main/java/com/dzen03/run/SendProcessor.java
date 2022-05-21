package com.dzen03.run;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveAction;

public class SendProcessor extends RecursiveAction
{
    byte[][] data;
    int begin, end;

    DatagramChannel channel;
    SocketAddress address;

    SendProcessor(byte[][] data, int begin, int end, DatagramChannel channel, SocketAddress address)
    {
        this.data = data;
        this.begin = begin;
        this.end = end;
        this.channel = channel;
        this.address = address;
    }

    @Override
    protected void compute() // [begin, end)
    {
        if (end - begin == 1)
        {
            try
            {
                channel.send(ByteBuffer.wrap(data[begin]), address);
//                LOGGER.log(Level.INFO, String.format("Sent packet num %d", begin));
            }
            catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }
        else
        {
            SendProcessor sendProcessor1 = new SendProcessor(data, begin, (begin + end) / 2, channel, address);
            SendProcessor sendProcessor2 = new SendProcessor(data, (begin + end) / 2, end, channel, address);

            sendProcessor1.fork();
            sendProcessor2.fork();
        }
    }
}
