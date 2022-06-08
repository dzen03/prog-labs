package com.dzen03.core;

public enum CommandType
{
    HELP,
    INFO,
    SHOW,
    ADD,
    UPDATE,
    REMOVE_BY_ID,
    CLEAR,
    SAVE,
    EXECUTE_SCRIPT,
    EXIT,
    REMOVE_FIRST,
    SHUFFLE,
    REMOVE_LOWER,
    MIN_BY_METERS_ABOVE_SEA_LEVEL,
    COUNT_LESS_THAN_GOVERNOR,
    FILTER_STARTS_WITH_NAME;

    public static CommandType pick(String name)
    {
        name = name.replace("\r", "");
        name = name.replace("\n", "");
//        System.out.println(name.toUpperCase());
        switch (name.toUpperCase())
        {
            case ("HELP"):
                return HELP;
            case ("INFO"):
                return INFO;
            case ("SHOW"):
                return SHOW;
            case ("ADD"):
                return ADD;
            case ("UPDATE"):
                return UPDATE;
            case ("REMOVE_BY_ID"):
                return REMOVE_BY_ID;
            case ("CLEAR"):
                return CLEAR;
            case ("SAVE"):
                return SAVE;
            case ("EXECUTE_SCRIPT"):
                return EXECUTE_SCRIPT;
            case ("EXIT"):
                return EXIT;
            case ("REMOVE_FIRST"):
                return REMOVE_FIRST;
            case ("SHUFFLE"):
                return SHUFFLE;
            case ("REMOVE_LOWER"):
                return REMOVE_LOWER;
            case ("MIN_BY_METERS_ABOVE_SEA_LEVEL"):
                return MIN_BY_METERS_ABOVE_SEA_LEVEL;
            case ("COUNT_LESS_THAN_GOVERNOR"):
                return COUNT_LESS_THAN_GOVERNOR;
            case ("FILTER_STARTS_WITH_NAME"):
                return FILTER_STARTS_WITH_NAME;
            default:
                throw new IncorrectCommandException();
        }
    }
}
