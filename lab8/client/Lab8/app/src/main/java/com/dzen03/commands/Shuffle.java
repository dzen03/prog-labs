package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class Shuffle extends Command
{
    @Override
    public String execute(String[] args)
    {
        Data.shuffle();
        return "Shuffled";
    }
}
