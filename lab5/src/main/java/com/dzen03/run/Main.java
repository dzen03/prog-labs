package com.dzen03.run;

import com.dzen03.core.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

//    public static final String ANSI_RESET = ANSI_GREEN;


    public static void main(String[] args)
    {
        System.out.print(ANSI_RESET);

        if (args.length == 0)
        {
            System.out.println(ANSI_RED + "You need to specify file!" + ANSI_RESET);
            return;
        }


        FileInputStream file;
        try
        {
            file = new FileInputStream(args[0]);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            return;
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

            inputStream.close();
            file.close();
        }
        catch (IOException ex)
        {
            System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
            return;
        }

        String[] packedCities = inputData.toString().split("\n");

        for (String packedCity: packedCities)
        {
            try
            {
                Data.citiesVector.add(new City(packedCity.split(",")));
            }
            catch (IncorrectFieldException | NumberFormatException | ArrayIndexOutOfBoundsException ex)
            {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
//                ex.printStackTrace();
                return;
            }
        }

//        Collections.sort(Data.citiesVector);

        System.out.print(ANSI_GREEN);

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine())
        {
//            String[] line = in.nextLine().split(" ");
            ArrayList<String> line = new ArrayList<>(Arrays.asList(in.nextLine().split(" ")));

            System.out.print(ANSI_RESET);

            if (line.size() == 0 || line.get(0).equals(""))
                continue;

            try
            {
                CommandType commandType = CommandType.pick(line.get(0));
                Command command = new CommandFactory().createCommand(commandType);
                if (commandType == CommandType.SAVE)
                {
                    line.add(args[0]);
                }
                System.out.println(ANSI_YELLOW + command.execute(line.toArray(new String[0])) + ANSI_RESET);
            }
            catch (ShutdownException ex)
            {
                System.out.println(ANSI_RED + "Shutting down" + ANSI_RESET);
                break;
            }
            catch (IncorrectCommandException | IncorrectFieldException ex)
            {
                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
                continue;
            }
//            catch (Exception ex)
//            {
//                System.out.println(ANSI_RED + ex.getMessage() + ANSI_RESET);
//            }
            finally
            {
                System.out.print(ANSI_GREEN);
            }

        }
        System.out.print(ANSI_RESET);
    }

}
