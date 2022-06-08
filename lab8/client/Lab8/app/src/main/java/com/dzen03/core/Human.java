package com.dzen03.core;

import java.io.Serializable;

public class Human implements Serializable
{
    private String name;

    public Human(String name)
    {
        if (name == null || name.equals(""))
            throw new IncorrectFieldException();
        this.name = name;
    }

    public Human()
    {
        this.name = "";
    }

    public String getName()
    {
        return name;
    }

    public Human setName(String name)
    {
        if (name == null || name.equals(""))
            return null;
        this.name = name;
        return this;
    }

    @Override
    public String toString()
    {
        return "Human{" +
                "name='" + name + '\'' +
                '}';
    }

    public int compareTo(Human human)
    {
        return this.getName().compareTo(human.getName());
    }
}

