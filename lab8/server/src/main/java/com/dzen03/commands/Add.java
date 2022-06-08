package com.dzen03.commands;

import com.dzen03.core.*;
import com.dzen03.run.ArgumentType;
import com.dzen03.run.Data;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

import static com.dzen03.run.Main.*;


public class Add extends Command
{
    @Override
    public String[] preExecute(String[] args)
    {
        String[] arg = null;
        if (args.length == 2)
            arg = args[1].split(",");
        else
        {
            arg = new String[args.length - 1];
            for (int i = 0; i < arg.length; ++i)
                arg[i] = args[i + 1];
        }
        City city = new City(arg);
        return city.dump().split(",");
    }

    @Override
    public String execute(String[] args)
    {
        if (args.length > 1)
        {
            City city = new City(args);
            try
            {
                Data.add(city);
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
            return "Added";
        }
        throw new IncorrectCommandException();
    }

    @Override
    public String executeFile(String[] args)
    {
        City city = new City();

        if (args.length > 1)
        {
            city.setName(args[1]);
            city.setArea(Float.parseFloat(args[2]));
            city.setPopulation(Integer.parseInt(args[3]));
            city.setMetersAboveSeaLevel(Integer.parseInt(args[4]));
            city.setCarCode(Integer.parseInt(args[5]));

            Coordinates coordinates = new Coordinates();
            String[] tmp = args[6].split(" ");
            coordinates.setX(Integer.parseInt(tmp[0]));
            coordinates.setY(Float.parseFloat(tmp[1]));
            city.setCoordinates(coordinates);

            city.setGovernment(Government.pick(args[7].toUpperCase()));

            city.setStandardOfLiving(StandardOfLiving.pick(args[8].toUpperCase()));

            Human human = new Human();
            city.setGovernor(human.setName(args[9]));

            city.setUserId(Integer.parseInt(args[10]));

            try
            {
                Data.add(city);
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }

        }
        return "Added";
    }

    @Override
    public ArgumentType[] getArgs() {
        return new ArgumentType[]{ArgumentType.CITY};
    }
}
