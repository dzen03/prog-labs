package com.dzen03.subjects;

import com.dzen03.core.*;

public class Compositions  extends Subject {
    public Compositions()
    {
        super(FaceType.THEY, "композиции", "странные");
    }

    public Object beOf()
    {
        Thing quartzite = new Thing("кварцита", "из которого больше нигде вокруг не было", "легкого", "архейского");

        Action beOf = new Action(ActionType.BE, quartzite);

        return this.doAction(this, beOf);
    }

    public Object scareAndWorry()
    {
        Thing shift = new Thing("чередований", "удивительной равномерностью", "своих");

        Action scareAndWorry = new Action(ActionType.SCARE_WORRY, shift);

        return this.doAction(this, scareAndWorry);
    }

}
