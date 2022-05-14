package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

import java.util.Collections;

public class Shuffle extends Command
{
    @Override
    public String execute(String[] args)
    {
        Collections.shuffle(Data.citiesVector);
        return "Shuffled";
    }
}
