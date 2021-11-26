package com.dzen03.core;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Person{" +
                "face=" + face +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return face == person.face && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(face, name);
    }
}
