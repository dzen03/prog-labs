package com.dzen03.run.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class DatagramServer {
    public static Object[] startServer(int port) throws IOException
    {
        Selector selector = Selector.open();
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(port));
        channel.register(selector, SelectionKey.OP_READ);

        return new Object[]{channel, selector};
    }
}
