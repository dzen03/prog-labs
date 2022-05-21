package com.dzen03.commands;

import com.dzen03.core.City;
import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class Show extends Command
{
    @Override
    public String execute(String[] args)
    {
        StringBuilder output = new StringBuilder();
        for (City city : Data.getCitiesVector())
        {
            output.append(city.toString()).append("\n");
        }
        return output.toString();
    }
}
