package com.dzen03.run;

import com.dzen03.core.City;
import com.dzen03.core.Command;

import java.io.Serializable;

public class CommandClass implements Serializable
{
    public Command type;
    public String[] args;
    public City city;
    CommandClass(Command type, String[] args)
    {
        this.type = type;
        this.args = args;
    }

    CommandClass(Command type, City city)
    {
        this.type = type;
        this.city = city;
    }
}
