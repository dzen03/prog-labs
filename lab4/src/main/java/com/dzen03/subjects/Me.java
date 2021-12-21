package com.dzen03.subjects;

import com.dzen03.core.*;

public class Me extends Person {
    public Me()
    {
        super(FaceType.I, "я");
    }

    public Object see()
    {
        Thing it = new Thing("их", "в бинокль");

        Action see = new Action(ActionType.SEE, it);

        return this.doAction(this, see);
    }

    public Object shoot()
    {
        Action film = new Action(ActionType.SHOOT, "аэрокамерой");

         return this.doAction(this, film);
    }

    public Object swap()
    {
        Thing buddy = new Thing("товарища", "своего");

        Action swap = new Action(ActionType.SWAP, buddy, new Thing("иногда"));

         return this.doAction(this, swap);
    }

    public Object beNotAsGood()
    {
        Thing skill = new Thing("в пилотировании","не так хорош");

        Action was = new Action(ActionType.BE, skill);

        return this.doAction(this, was);
    }

    public Object guess()
    {
        Thing explanation = new Thing("над объяснением этого");

        Action guess = new Action(ActionType.GUESS, explanation);

        return this.doAction(this, guess);
    }

    public Object feel()
    {
        Thing shame = new Thing("посрамленным", "себя", "как геолог");

        Action feel = new Action(ActionType.FEEL, shame);

        return this.doAction(this, feel);
    }

}
