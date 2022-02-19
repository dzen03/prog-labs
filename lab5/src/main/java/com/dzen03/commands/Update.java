package com.dzen03.commands;

import com.dzen03.core.*;
import com.dzen03.run.Data;

import java.util.Scanner;

import static com.dzen03.run.Main.*;
import static com.dzen03.run.Main.ANSI_RESET;

public class Update extends Command
{
    public static final String NAME_COLOUR = ANSI_PURPLE;
    public static final String INPUT_COLOUR = ANSI_CYAN;

    @Override
    public String execute(String[] args)
    {

        City city = new City();

        if (args.length != 2)
        {
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

            Data.citiesVector.add(city);
            return "Added";
        }
        Scanner in = new Scanner(System.in);

        long id = Long.parseLong(args[1]);
        int index = -1;


        for (int i = 0; i < Data.citiesVector.size(); i++)
        {
            if (Data.citiesVector.get(i).getId() == id)
            {
                index = i;
                city = Data.citiesVector.get(i);
            }
        }

        if (city == null)
            throw new IncorrectCommandException("No such ID");

        while (true)
        {
            System.out.print(NAME_COLOUR + "Name: " + INPUT_COLOUR);
            try
            {
                city.setName(in.nextLine());
                break;
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
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
            } catch (Exception ex)
            {
                System.out.println(ANSI_RED + "Retry" + ANSI_RESET);
            }
        }

        Data.citiesVector.set(index, city);

        return "Updated";
    }
}
