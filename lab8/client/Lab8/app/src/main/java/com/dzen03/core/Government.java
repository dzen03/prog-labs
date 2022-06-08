package com.dzen03.core;

import java.io.Serializable;

public enum Government implements Serializable
{
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
            case "null":
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

}