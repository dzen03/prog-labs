package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.core.IncorrectCommandException;
import com.dzen03.run.ArgumentType;
import com.dzen03.run.Data;

public class FilterStartsWithName extends Command
{
    @Override
    public ArgumentType[] getArgs() {
        return new ArgumentType[]{ArgumentType.STRING};
    }

    @Override
    public String execute(String[] args)
    {
        if (args.length < 2)
            throw new IncorrectCommandException();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < Data.getCitiesVector().size(); ++i)
        {
            if (Data.getCitiesVector().get(i).getName().startsWith(args[1]))
                output.append(Data.getCitiesVector().get(i).toString()).append("\n");
        }
        return output.toString();
    }
}
