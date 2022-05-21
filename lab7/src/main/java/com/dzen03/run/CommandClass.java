package com.dzen03.run;

import com.dzen03.core.City;
import com.dzen03.core.Command;

import java.io.Serializable;

public class CommandClass implements Serializable
{
    public Command type;
    public String[] args;
    public String login;
    public String pass;

    public City city;

    CommandClass(Command type, String[] args, String login, String pass)
    {
        this.type = type;
        this.args = args;
        this.login = login;
        this.pass = pass;
    }

    CommandClass(Command type, City city)
    {
        this.type = type;
        this.args = args;
        this.login = login;
        this.pass = pass;
    }
}
