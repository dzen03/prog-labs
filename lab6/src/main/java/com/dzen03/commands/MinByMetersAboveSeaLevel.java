package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class MinByMetersAboveSeaLevel extends Command
{
    @Override
    public String execute(String[] args)
    {
        int index = -1, min = Integer.MAX_VALUE;
        for (int i = 0; i < Data.citiesVector.size(); ++i)
        {
            if (Data.citiesVector.get(i).getMetersAboveSeaLevel() < min)
            {
                index = i;
                min = Data.citiesVector.get(i).getMetersAboveSeaLevel();
            }
        }

        return Data.citiesVector.get(index).toString();
    }
}
