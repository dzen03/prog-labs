package com.dzen03.commands;

import com.dzen03.core.Command;
import com.dzen03.core.CommandFactory;
import com.dzen03.core.CommandType;
import com.dzen03.run.ArgumentType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import static com.dzen03.run.Main.LOGGER;

public class ExecuteScript extends Command
{
    @Override
    public ArgumentType[] getArgs() {
        return new ArgumentType[]{ArgumentType.STRING};
    }

    @Override
    public String execute(String[] args)
    {
        String inputData = args[1];

        StringBuilder output = new StringBuilder();


        String[] commands = inputData.split("[\n\r]+");


        for (int index = 0; index < commands.length; ++index)
        {
            try
            {ArrayList<String> line = new ArrayList<>(Arrays.asList(commands[index].split(" ")));

                if (line.size() == 0 || line.get(0).equals(""))
                {
                    continue;
                }

                CommandType commandType = CommandType.pick(line.get(0));
                Command command = new CommandFactory().createCommand(commandType);
                if (commandType == CommandType.ADD || commandType == CommandType.UPDATE || commandType == CommandType.REMOVE_LOWER)
                {
                    line.addAll(Arrays.asList(commands).subList(1 + index, index + 10));
                    index += 9;
                }
                else if (commandType == CommandType.COUNT_LESS_THAN_GOVERNOR)
                {
                    line.add(commands[index + 1]);
                    index += 1;
                }
                line.add(args[2]);
                output.append(command.executeFile(line.toArray(new String[0]))).append("\n");
            }
            catch (Exception ex)
            {
                output.append(ex.getMessage()).append("\n");
            }


        }

        return output.toString();
    }
}
