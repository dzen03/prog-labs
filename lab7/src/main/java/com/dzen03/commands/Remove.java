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
        int user_id;
        try
        {
            id = Long.parseLong(args[1]);
            user_id = Integer.parseInt(args[2]);
        }
        catch (Exception ex)
        {
            throw new IncorrectCommandException();
        }

        try
        {
            Data.remove(id, user_id);
        }
        catch (Exception ex)
        {
            return ex.getMessage();
        }
        return "Removed";
    }
}
