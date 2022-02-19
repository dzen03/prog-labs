package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.core.Human;
import com.dzen03.run.Data;

import java.util.Scanner;

import static com.dzen03.run.Main.*;
import static com.dzen03.run.Main.ANSI_CYAN;

public class CountLessThanGovernor extends Command
{
    public static final String NAME_COLOUR = ANSI_PURPLE;
    public static final String INPUT_COLOUR = ANSI_CYAN;
    @Override
    public String execute(String[] args)
    {

        Human human = new Human();

        if (args.length != 1)
        {
            human = human.setName(args[1]);
        }

        Scanner in = new Scanner(System.in);
        while (true) { System.out.println(NAME_COLOUR + "Governor: " + INPUT_COLOUR);
            try{ human = human.setName(in.nextLine()); human.getName(); break; }
            catch (Exception ex) { System.out.println(ANSI_RED + "Retry" + ANSI_RESET); } }

        int cnt = 0;
        for (int i = 0; i < Data.citiesVector.size(); ++i)
        {
            if (Data.citiesVector.get(i).getGovernor().compareTo(human) < 0)
                cnt += 1;
        }

        return String.valueOf(cnt);
    }
}
