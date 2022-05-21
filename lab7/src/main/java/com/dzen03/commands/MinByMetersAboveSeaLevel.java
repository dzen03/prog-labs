package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.run.Data;

public class MinByMetersAboveSeaLevel extends Command
{
    @Override
    public String execute(String[] args)
    {
        int index = -1, min = Integer.MAX_VALUE;
        for (int i = 0; i < Data.getCitiesVector().size(); ++i)
        {
            if (Data.getCitiesVector().get(i).getMetersAboveSeaLevel() < min)
            {
                index = i;
                min = Data.getCitiesVector().get(i).getMetersAboveSeaLevel();
            }
        }

        return Data.getCitiesVector().get(index).toString();
    }
}
