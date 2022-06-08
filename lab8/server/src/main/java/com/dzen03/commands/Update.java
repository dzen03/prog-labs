package com.dzen03.commands;

import com.dzen03.core.*;
import com.dzen03.run.ArgumentType;
import com.dzen03.run.Data;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

import static com.dzen03.run.Main.*;

public class Update extends Command
{
    public static final String NAME_COLOUR = ANSI_PURPLE;
    public static final String INPUT_COLOUR = ANSI_CYAN;

    @Override
    public ArgumentType[] getArgs() {
        return new ArgumentType[]{ArgumentType.INT, ArgumentType.CITY};
    }

    @Override
    public String[] preExecute(String[] args)
    {
        City city = new City();

        Scanner in = new Scanner(System.in);

        long id;
        try
        {
            id = Long.parseLong(args[1]);
        }
        catch (Exception ex)
        {
            throw new IncorrectCommandException("Incorrect ID");
        }


        while (true)
        {
            System.out.print(NAME_COLOUR + "Name: " + INPUT_COLOUR);
            try
            {
                city.setName(in.nextLine());
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
            }
        }
        while (true)
        {
            System.out.print(NAME_COLOUR + "Area: " + INPUT_COLOUR);
            try
            {
                city.setArea(in.nextFloat());
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
                in.nextLine();
            }
        }
        while (true)
        {
            System.out.print(NAME_COLOUR + "Population: " + INPUT_COLOUR);
            try
            {
                city.setPopulation(in.nextInt());
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
                in.nextLine();
            }
        }
        while (true)
        {
            System.out.print(NAME_COLOUR + "Meters above sea level: " + INPUT_COLOUR);
            try
            {
                city.setMetersAboveSeaLevel(in.nextInt());
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
                in.nextLine();
            }
        }
        while (true)
        {
            System.out.print(NAME_COLOUR + "Car code: " + INPUT_COLOUR);
            try
            {
                city.setCarCode(in.nextInt());
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
                in.nextLine();
            }
        }

        in.nextLine();

        Coordinates coordinates = new Coordinates();
        while (true)
        {
            System.out.println(NAME_COLOUR + "Coordinates: " + INPUT_COLOUR);
            try
            {
                String[] tmp = in.nextLine().split(" ");
                coordinates.setX(Integer.parseInt(tmp[0]));
                coordinates.setY(Float.parseFloat(tmp[1]));
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
            }
        }
        city.setCoordinates(coordinates);

        while (true)
        {
            System.out.println(NAME_COLOUR + "Goverment (ARISTOCRACY/DICTATORSHIP/MATRIARCHY/MONARCHY/PLUTOCRACY): " + INPUT_COLOUR);
            try
            {
                city.setGovernment(Government.pick(in.nextLine().toUpperCase()));
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
            }
        }


        while (true)
        {
            System.out.println(NAME_COLOUR + "Standart of living (MEDIUM/ULTRA_LOW/NIGHTMARE): " + INPUT_COLOUR);
            try
            {
                city.setStandardOfLiving(StandardOfLiving.pick(in.nextLine().toUpperCase()));
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
            }
        }


        while (true)
        {
            System.out.println(NAME_COLOUR + "Governor: " + INPUT_COLOUR);
            Human human = new Human();
            try
            {
                city.setGovernor(human.setName(in.nextLine()));
                break;
            }
            catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
            }
        }
        city.setId(id);
        String[] tmp = city.dumpForDB().replace("'", "").split(",");
//        String[] res = new String[tmp.length + 1];
//        res[res.length - 1] = String.valueOf(id);
//        for (int i = 0; i < tmp.length; ++i)
//            res[i] = tmp[i];

        return tmp;
    }

    @Override
    public String execute(String[] args)
    {
        LOGGER.log(Level.INFO, Arrays.toString(args));
        City city = new City();

        if (args.length > 2)
        {
//            long id = Long.parseLong(args[args.length - 2]);
//            int user_id = Integer.parseInt(args[args.length - 1]);
            city = new City(args);
//            city.setId(id);

            long id = city.getId();
            int user_id = city.getUserId();
            try
            {
                Data.update(id, city, user_id);
            }
            catch (Exception ex)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }

        }

        return "Updated";
    }

    @Override
    public String executeFile(String[] args)
    {

        City city = new City();

        if (args.length > 2)
        {
            city.setId(Long.parseLong(args[1]));

            city.setName(args[2]);
            city.setArea(Float.parseFloat(args[3]));
            city.setPopulation(Integer.parseInt(args[4]));
            city.setMetersAboveSeaLevel(Integer.parseInt(args[5]));
            city.setCarCode(Integer.parseInt(args[6]));

            Coordinates coordinates = new Coordinates();
            String[] tmp = args[7].split(" ");
            coordinates.setX(Integer.parseInt(tmp[0]));
            coordinates.setY(Float.parseFloat(tmp[1]));
            city.setCoordinates(coordinates);

            city.setGovernment(Government.pick(args[8].toUpperCase()));

            city.setStandardOfLiving(StandardOfLiving.pick(args[9].toUpperCase()));

            Human human = new Human();
            city.setGovernor(human.setName(args[10]));

            city.setUserId(Integer.parseInt(args[11]));

            try
            {
                Data.update(city.getId(), city, city.getUserId());
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
        }
        return "Updated";
    }
}
