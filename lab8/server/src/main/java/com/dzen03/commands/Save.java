package com.dzen03.commands;

import com.dzen03.core.City;
import com.dzen03.core.Command;
import com.dzen03.core.IncorrectCommandException;
import com.dzen03.run.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Save extends Command
{
    @Override
    public String[] preExecute(String[] args)
    {
        throw new IncorrectCommandException("Not allowed to do this!");
    }

    @Override
    public String execute(String[] args)
    {
        FileOutputStream file;
        try
        {
            file = new FileOutputStream(args[1]);
        }
        catch (FileNotFoundException ex)
        {
            return ex.getMessage();
        }

        OutputStreamWriter outputStream = new OutputStreamWriter(file);
        StringBuilder output = new StringBuilder();

        for (City city : Data.getCitiesVector())
        {
            output.append(city.dump()).append("\n");
        }

        System.out.println(args[1]);

        try
        {
            outputStream.write(output.toString());

            outputStream.flush();

            outputStream.close();
            file.close();
        }
        catch (IOException ex)
        {
            return ex.getMessage();
        }

        return "Saved";
    }

}
