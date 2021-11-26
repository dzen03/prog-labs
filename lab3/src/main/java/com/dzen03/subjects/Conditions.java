package com.dzen03.subjects;

import com.dzen03.core.*;

public class Conditions extends Subject {
    public Conditions()
    {
        super(FaceType.THEY, new Thing("атмосферные условия", new Description("в этом таинственном районе"), new Description("некие"), new Description("особые")));
    }

    public Object preventHold()
    {
        Thing changes = new Thing(" переменам");

        Thing destruction = new Thing("процесс разрушения горных пород", "обычный");

        Action prevent = new Action(ActionType.PREVENT, changes);
        Action hold = new Action(ActionType.HOLD_BACK, destruction);

        return this.doActions(this, prevent, hold);
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
//    хотя по всем приметам некие особые атмосферные условия в этом таинственном районе препятствовали переменам,