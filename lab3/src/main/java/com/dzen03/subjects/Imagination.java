package com.dzen03.subjects;

import com.dzen03.core.*;

public class Imagination extends Subject {
    public Imagination()
    {
        super(FaceType.IT, "воображение", "наше");
    }

    public Object worryTease()
    {
        Thing cubes = new Thing("кубы", " правильной формы");
        Thing caves = new Thing("пещеры");
        Thing rampart = new Thing("валы", "крепостные");

        Action worryTease = new Action(ActionType.WORRY_TEASE, cubes, caves, rampart);

        return this.doAction(this, worryTease);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
