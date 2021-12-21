package com.dzen03.subjects;

import com.dzen03.core.*;

public class Danford extends Person {

    public Danford()
    {
        super(FaceType.HE, "Денфорт");
    }


    public Object pilotPlane()
    {
        // TODO add exception
        Thing plane = new Thing("самолет");
        Action pilot = null;
        if (Math.random() > 0.5)
            pilot = new Action(ActionType.PILOT, plane);
        else
            pilot = new Action(ActionType.SHOOT, plane);

        if (!pilot.getActionType().equals(ActionType.PILOT))
            throw new PlaneCrashException("Самолет упал =(");

        return this.doAction(this, pilot);
    }

    public Object see()
    {
        Thing it = new Thing("их", "тогда в бинокль");

        Action see = new Action(ActionType.SEE, it);

        return this.doAction(this, see);
    }

}
