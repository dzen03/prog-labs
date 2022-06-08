package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.core.Human;
import com.dzen03.run.ArgumentType;
import com.dzen03.run.Data;

import java.util.Arrays;
import java.util.Scanner;

import static com.dzen03.run.Main.*;

import android.util.Log;

public class CountLessThanGovernor extends Command
{
    @Override
    public ArgumentType[] getArgs() {
        return new ArgumentType[]{ArgumentType.STRING};
    }

    public static final String NAME_COLOUR = ANSI_PURPLE;
    public static final String INPUT_COLOUR = ANSI_CYAN;

    @Override
    public String[] preExecute(String[] args)
    {
        Human human = new Human();

        if (args.length != 1)
        {
            human = human.setName(args[1]);
        }

        return new String[]{human.getName()};
    }

    @Override
    public String execute(String[] args)
    {

        Human human = new Human();

        if (args.length != 1)
        {
            human = human.setName(args[1]);
        }
        else
            human = human.setName(args[0]);

        int cnt = 0;
        for (int i = 0; i < Data.getCitiesVector().size(); ++i)
        {
            if (Data.getCitiesVector().get(i).getGovernor() == null ||
                    Data.getCitiesVector().get(i).getGovernor().compareTo(human) < 0)
                cnt += 1;
        }

        return String.valueOf(cnt);
    }
}
