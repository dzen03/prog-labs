package com.dzen03.subjects;

import com.dzen03.core.*;

public class Edges extends Subject {
    public Edges()
    {
        super(FaceType.THEY, new Thing("края этих каменных фигур"));
    }

    public Object crumbleAndRound()
    {
        Thing years = new Thing("годы", "за долгие");
        Action crumbleAndRound = new Action(ActionType.CRUMBLE_ROUND_SELF, years);

        return this.doAction(this, crumbleAndRound);
    }

}
