package com.dzen03.core;

public enum StandardOfLiving
{
    MEDIUM,
    ULTRA_LOW,
    NIGHTMARE;

    public static StandardOfLiving pick(String type)
    {
        switch (type)
        {
            case "":
                return null;
            case "MEDIUM":
                return MEDIUM;
            case "ULTRA_LOW":
                return ULTRA_LOW;
            case "NIGHTMARE":
                return NIGHTMARE;
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
