package com.dzen03.subjects;

import com.dzen03.core.*;

public class Giants extends Subject {
    public Giants()
    {
        super(FaceType.THEY, new Thing("гиганты", "эти"));
    }

    public Object stand()
    {
        Thing times = new Thing("времена", new Description("в давние"), new Description("50 миллионов лет назад"));
        Action stand = new Action(ActionType.BE, times);
        return this.doAction(this, stand);
    }
}
