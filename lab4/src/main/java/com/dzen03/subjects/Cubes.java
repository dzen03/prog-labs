package com.dzen03.subjects;

import com.dzen03.core.*;

public class Cubes extends Subject {
    public Cubes()
    {
        super(FaceType.THEY, "части кубов", "нижние примыкающие к склону");
    }

    public Object seem()
    {
        Thing soil = new Thing("на породы хребтов", "похожи");

        Action seem = new Action(ActionType.BE, soil);

        return this.doAction(this, seem);
    }

}
