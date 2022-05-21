package com.dzen03.commands;

import com.dzen03.core.*;
import com.dzen03.run.Data;

import java.util.Scanner;
import java.util.logging.Level;

import static com.dzen03.run.Main.*;

public class RemoveLower extends Command
{
    public static final String NAME_COLOUR = ANSI_PURPLE;
    public static final String INPUT_COLOUR = ANSI_CYAN;

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
        String[] tmp = city.dump().split(",");
        String[] res = new String[tmp.length + 1];
        res[res.length - 1] = String.valueOf(id);
        for (int i = 0; i < tmp.length; ++i)
            res[i] = tmp[i];

        return res;
    }

    @Override
    public String execute(String[] args)
    {

        City city = new City();

        if (args.length > 2)
        {
            long id = Long.parseLong(args[args.length - 2]);
            int user_id = Integer.parseInt(args[args.length - 1]);
            city = new City(args);
            city.setId(id);

            try
            {
                Data.remove_lower(city, user_id);
            }
            catch (Exception ex)
            {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }

        }

        return "Updated";
    }
}
