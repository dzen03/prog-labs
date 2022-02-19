package com.dzen03.core;

import java.util.NoSuchElementException;

public enum Government {
    ARISTOCRACY,
    DICTATORSHIP,
    MATRIARCHY,
    MONARCHY,
    PLUTOCRACY;

    public static Government pick(String type)
    {
        switch (type)
        {
            case "":
                return null;
            case "ARISTOCRACY":
                return ARISTOCRACY;
            case "DICTATORSHIP":
                return DICTATORSHIP;
            case "MATRIARCHY":
                return MATRIARCHY;
            case "MONARCHY":
                return MONARCHY;
            case "PLUTOCRACY":
                return PLUTOCRACY;
            default:
                throw new IncorrectFieldException();
        }
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}