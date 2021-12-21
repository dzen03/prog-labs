package com.dzen03.subjects;

import com.dzen03.core.*;

public class StoneStrength extends Subject {
    public StoneStrength()
    {
        super(FaceType.SHE, new Thing("прочность камня", "исключительная"));
    }

    public Object help_to_stay()
    {
        Action help_to_stay = new Action(ActionType.HELP_TO_STAY, "ему");

        return this.doAction(this, help_to_stay);
    }
}
