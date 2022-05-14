package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.core.IncorrectCommandException;
import com.dzen03.run.Data;

public class Remove extends Command
{
    @Override
    public String execute(String[] args)
    {
        long id;
        try
        {
            id = Long.parseLong(args[1]);
        }
        catch (Exception ex)
        {
            throw new IncorrectCommandException();
        }

        for (int i = 0; i < Data.citiesVector.size(); i++)
        {
            if (Data.citiesVector.get(i).getId() == id)
            {
                Data.citiesVector.remove(i);
                return "Removed";
            }
        }
        throw new IncorrectCommandException("No such ID");
    }
}
