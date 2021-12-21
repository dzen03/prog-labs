package com.dzen03.core;

import java.util.Arrays;
import java.util.Objects;

public class Thing implements Everything {
    private String name;
    private Description[] descriptions;

    public Thing()
    {
        this.name = "";
        this.descriptions = new Description[]{};
    }

    public Thing(String name)
    {
        this.name = name;
        this.descriptions = new Description[]{};
    }

    public Thing(String name, String... description)
    {
        this.name = name;
        Description[] descriptions = new Description[description.length];
        for (int index = 0; index < description.length; ++index)
        {
            descriptions[index] = new Description(description[index]);
        }

        this.descriptions = descriptions;
    }

    public Thing(String name, Thing description) {
        this.name = name;
        this.descriptions = new Description[]{new Description(description)};
    }

    public Thing(String name, Description... description)
    {
        this.name = name;
        this.descriptions = description;
    }

    public Thing(Thing thing) {
        this.name = thing.name;
        this.descriptions = thing.descriptions;
    }

    @Override
    public String toString() {
        // TODO another exception + handler
        try
        {
            StringBuilder result = new StringBuilder();
            for (Description desc: descriptions)
            {
                result.append(desc.toString());
            }
            result.append(" ");
            result.append(name);
            if (result.length() == 0)
                throw new IncorrectThingException("Неверная вещь");
            return result.toString();
        }
        catch (IncorrectThingException exception)
        {
            System.out.println(exception.getMessage());
            return "";
        }
    }

    @Override
    public Object doAction(Object thing, Action actions) {
        return thing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return Objects.equals(name, thing.name) && Arrays.equals(descriptions, thing.descriptions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(descriptions);
        return result;
    }
}
