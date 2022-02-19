package com.dzen03.core;

public class Human {
    private String name; //Поле не может быть null, Строка не может быть пустой

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

