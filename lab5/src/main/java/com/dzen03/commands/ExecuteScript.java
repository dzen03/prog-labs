package com.dzen03.commands;

import com.dzen03.core.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ExecuteScript extends Command
{
    @Override
    public String execute(String[] args)
    {
//        System.out.println(args[0]);
        FileInputStream file;
        try
        {
            file = new FileInputStream(args[1]);
        }
        catch (FileNotFoundException ex)
        {
            return "Incorrect file";
        }
        InputStreamReader inputStream = new InputStreamReader(file);
        StringBuilder inputData = new StringBuilder();

        try
        {
            int data = inputStream.read();

            while (data != -1)
            {
                inputData.append((char) data);
                data = inputStream.read();
            }
        }
        catch (IOException ex)
        {
             return "Incorrect file";
        }

//        System.out.println(inputData);

        StringBuilder output = new StringBuilder();


        String[] commands = inputData.toString().split("[\n\r]+");


        for (int index = 0; index < commands.length; ++index)
        {
            ArrayList<String> line = new ArrayList<>(Arrays.asList(commands[index].split(" ")));

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
            output.append(command.execute(line.toArray(new String[0])));

        }

        return output.toString();
    }
}
