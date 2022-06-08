package com.dzen03.core;

import com.dzen03.commands.*;

public class CommandFactory
{
    public final Command createCommand(CommandType commandType)
    {
        Command command = null;

        switch (commandType)
        {
            case HELP:
                command = new Help();
                break;
            case EXIT:
                command = new Exit();
                break;
            case SHOW:
                command = new Show();
                break;
            case ADD:
                command = new Add();
                break;
            case UPDATE:
                command = new Update();
                break;
            case REMOVE_BY_ID:
                command = new Remove();
                break;
            case CLEAR:
                command = new Clear();
                break;
            case EXECUTE_SCRIPT:
                command = new ExecuteScript();
                break;
            case SAVE:
                command = new Save();
                break;
            case REMOVE_FIRST:
                command = new RemoveFirst();
                break;
            case SHUFFLE:
                command = new Shuffle();
                break;
            case REMOVE_LOWER:
                command = new RemoveLower();
                break;
            case MIN_BY_METERS_ABOVE_SEA_LEVEL:
                command = new MinByMetersAboveSeaLevel();
                break;
            case COUNT_LESS_THAN_GOVERNOR:
                command = new CountLessThanGovernor();
                break;
            case FILTER_STARTS_WITH_NAME:
                command = new FilterStartsWithName();
                break;
            case INFO:
                command = new Info();
                break;
            default:
                throw new IncorrectCommandException();
        }
        return command;
    }
}
