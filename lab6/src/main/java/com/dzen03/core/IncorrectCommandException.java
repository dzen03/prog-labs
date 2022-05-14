package com.dzen03.core;

public class IncorrectCommandException extends RuntimeException
{

    public IncorrectCommandException(String text)
    {
        super(text);
    }

    public IncorrectCommandException()
    {
        super("Incorrect command");
    }
}
