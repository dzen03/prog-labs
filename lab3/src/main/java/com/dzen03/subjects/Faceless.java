package com.dzen03.subjects;

import com.dzen03.core.*;

public class Faceless extends Person {
    public Faceless()
    {
        super(FaceType.NONE, "");
    }


    public Object guess()
    {
        Thing useless = new Thing("представлялось бессмыссленным", "насколько выше они были тогда");
        Action guess = new Action(ActionType.GUESS, useless);
        return this.doAction(this, guess);
    }
}
