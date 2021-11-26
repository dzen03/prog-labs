package com.dzen03.subjects;

import com.dzen03.core.*;

public class Danford extends Person {

    public Danford()
    {
        super(FaceType.HE, "Денфорт");
    }


    public Object flyPlane()
    {
        Thing plane = new Thing("самолет");

        Action fly = new Action(ActionType.FLY, plane);

         return this.doAction(this, fly);
    }

    public Object see()
    {
        Thing it = new Thing("их", "тогда в бинокль");

        Action see = new Action(ActionType.SEE, it);

        return this.doAction(this, see);
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
