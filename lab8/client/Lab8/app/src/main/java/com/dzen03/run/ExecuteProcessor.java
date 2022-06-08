package com.dzen03.run;

import com.dzen03.core.Command;
import com.dzen03.lab8.MainActivity;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveAction;

public class ExecuteProcessor extends RecursiveAction
{
    final Command command;
    final String[] args;
    final DatagramChannel channel;
    final SocketAddress address;

    ExecuteProcessor(Command command, String[] args, DatagramChannel channel, SocketAddress address)
    {
        this.command = command;
        this.args = args;
        this.channel = channel;
        this.address = address;
    }

    @Override
    protected void compute()
    {
        String result = command.execute(args);

        send(DataConversion.dataToArrays(result.getBytes()));
    }

    private void send(byte[][] data)
    {
        SendProcessor sendProcessor = new SendProcessor(data, 0, data.length, channel, address);
        MainActivity.pool.execute(sendProcessor);
    }

}
