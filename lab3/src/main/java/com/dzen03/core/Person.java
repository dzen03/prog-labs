package com.dzen03.core;

public abstract class Person implements Everything{
    private FaceType face;
    private String name;

    public Person(FaceType face, String name)
    {
        this.face = face;
        this.name = name;
    }

    public FaceType getFaceType()
    {
        return face;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public Object doAction(Object person, Action action)
    {
        Prettify.PrettifyAndPrint(getName() +  action.getAction(getFaceType()));
        return person;
    }

}
