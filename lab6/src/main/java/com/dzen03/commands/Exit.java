package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.core.ShutdownException;

public class Exit extends Command
{
    @Override
    public String[] preExecute(String[] args) {throw new ShutdownException();}

    @Override
    public String execute(String[] args)
    {
        throw new ShutdownException();
    }
}
