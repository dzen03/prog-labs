package com.dzen03.core;

public class AuthException extends RuntimeException
{
    public AuthException(String text)
    {
        super(text);
    }

    public AuthException()
    {
        super();
    }
}
