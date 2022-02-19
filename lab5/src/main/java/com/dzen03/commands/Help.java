package com.dzen03.commands;

import com.dzen03.core.Command;

public class Help extends Command {
    @Override
    public String execute(String[] args)
    {
        return "help : show help for available commands\n" +
                "info : print information about the collection to standard output (type, initialization date, number of elements, etc.)\n" +
                "show : print to standard output all elements of the collection in string representation\n" +
                "add {element} : add a new element to the collection\n" +
                "update id {element} : update the value of the collection element whose id is equal to the given one\n" +
                "remove_by_id id : remove an element from the collection by its id\n" +
                "clear : clear the collection\n" +
                "save : save the collection to a file\n" +
                "execute_script file_name : read and execute script from specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.\n" +
                "exit : exit the program (without saving to a file)\n" +
                "remove_first : remove the first element from the collection\n" +
                "shuffle : shuffle the elements of a collection randomly\n" +
                "remove_lower {element} : remove from the collection all elements less than the given one\n" +
                "min_by_meters_above_sea_level : output any object from the collection whose metersAboveSeaLevel field value is the minimum\n" +
                "count_less_than_governor governor : print the number of elements whose governor field value is less than the given one\n" +
                "filter_starts_with_name name : display elements whose name field value starts with the given substring";
    }
}
