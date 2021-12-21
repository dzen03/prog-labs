package com.dzen03.core;

import java.util.Objects;

public abstract class Subject extends Thing{
    private FaceType face;

    public Subject(FaceType face, Thing name)
    {
        super(name);
        this.face = face;
    }

    public Subject(FaceType face, String name)
    {
        super(name);
        this.face = face;
    }

    public Subject(FaceType face, String name, String description)
    {
        super(name, description);
        this.face = face;
    }

    public Subject(FaceType face, String name, Description description)
    {
        super(name, description);
        this.face = face;
    }

    public FaceType getFaceType()
    {
        return face;
    }


    public Object doActions(Object subject, Action... actions)
    {
        StringBuilder result = new StringBuilder();
        for (Action action: actions)
        {
            result.append(action.getAction(getFaceType(), false, false)).append(", ");
        }
        if (result.length() > 3)
            result.delete(result.length() - 2, result.length());
//        System.out.println(toString() + result + ".");
        Prettify.PrettifyAndPrint(toString() + result + ".");
        return subject;
    }

    @Override
    public Object doAction(Object subject, Action action) {
        this.doActions(subject, action);

        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return face == subject.face;
    }

    @Override
    public int hashCode() {
        return Objects.hash(face);
    }
}
