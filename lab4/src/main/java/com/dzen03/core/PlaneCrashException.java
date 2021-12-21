package com.dzen03.core;

public class PlaneCrashException extends RuntimeException{
    public PlaneCrashException(String errorMessage)
    {
        super(errorMessage);
    }
}
