package com.dzen03.core;

import com.dzen03.run.ArgumentType;

import java.io.Serializable;

public abstract class Command implements Serializable
{
    public String execute(String[] args)
    {
        return args[0];
    }

    public String executeFile(String[] args)
    {
        return execute(args);
    }

    public String[] preExecute(String[] args)
    {
        return args;
    }

    public ArgumentType[] getArgs()
    {
        return new ArgumentType[]{};
    }
}
