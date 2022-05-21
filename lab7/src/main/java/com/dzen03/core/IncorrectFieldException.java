package com.dzen03.core;

public class IncorrectFieldException extends RuntimeException
{
    public IncorrectFieldException(String text)
    {
        super(text);
    }

    public IncorrectFieldException()
    {
        super("Incorrect data");
    }
}
