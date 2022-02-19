package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class FilterStartsWithName extends Command
{
    @Override
    public String execute(String[] args)
    {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < Data.citiesVector.size(); ++i)
        {
            if (Data.citiesVector.get(i).getName().startsWith(args[1]))
                output.append(Data.citiesVector.get(i).toString()).append("\n");
        }

        return output.toString();
    }
}
