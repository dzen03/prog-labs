package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class Clear extends Command
{
    @Override
    public String execute(String[] args)
    {
        int user_id = Integer.parseInt(args[args.length - 1]);
        try
        {
            Data.clear(user_id);
        }
        catch (Exception ex)
        {
            return ex.getMessage();
        }

        return "Cleared";
    }
}
