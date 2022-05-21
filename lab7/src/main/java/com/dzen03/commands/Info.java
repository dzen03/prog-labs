package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class Info extends Command
{
    @Override
    public String execute(String[] args)
    {
        return String.format("Creation date: %s\nType: %s\nSize: %d\nContent: %s", Data.creationDate.toString(),
                Data.getCitiesVector().getClass().toString(), Data.getCitiesVector().size(), Data.getCitiesVector());
    }
}
