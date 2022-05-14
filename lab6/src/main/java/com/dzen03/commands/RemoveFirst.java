package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class RemoveFirst extends Command
{
    @Override
    public String execute(String[] args)
    {
        Data.citiesVector.remove(0);

        return "Removed";
    }
}
