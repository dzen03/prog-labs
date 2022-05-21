package com.dzen03.core;

public class ShutdownException extends RuntimeException
{
    public ShutdownException(String text)
    {
        super(text);
    }

    public ShutdownException()
    {
        super();
    }
}
