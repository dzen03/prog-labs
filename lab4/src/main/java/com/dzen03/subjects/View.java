package com.dzen03.subjects;

import com.dzen03.core.*;

public class View extends Subject {

    public View()
    {
//        Thing soil = new Thing("пород", new Description("выветрившихся"), new Description("древних"));
        super(FaceType.HE, new Thing("вид", new Thing("пород",
                new Description("выветрившихся"), new Description("древних"))));
    }

    public Object beSimilar()
    {
        Thing LeicaDesc = new Thing("описаниям Лейка");
        Action equals = new Action(ActionType.EQUALS, LeicaDesc);
        return this.doAction(this, equals);
    }

}
