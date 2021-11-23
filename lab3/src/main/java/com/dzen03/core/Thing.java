package com.dzen03.core;

public class Thing implements Everything{
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
        StringBuilder result = new StringBuilder();
        for (Description desc: descriptions)
        {
            result.append(desc.toString());
        }
        return result + " " + name;
    }

    @Override
    public Object doAction(Object thing, Action actions) {
        return thing;
    }
}
