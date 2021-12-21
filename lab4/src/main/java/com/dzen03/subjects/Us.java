package com.dzen03.subjects;

import com.dzen03.core.*;

public class Us extends Person {
    public Us()
    {
        super(FaceType.US, "мы");
    }

    public Object view()
    {
        Thing snowLine = new Thing("снежной линией", new Description("отделяющей обнаженную породу от вечных льдов"));
        Description[] mountainDescs = new Description[]{new Description("мрачные"),
                new Description("грозно темнеющие над"), new Description(snowLine)};
        Thing mountain = new Thing("вершины", mountainDescs);


        Thing constructions = new Thing("конструкций", new Description("всё большее количество прилепившихся к"),
                new Description(new Thing("склонам", "горным")), new Description("геометрически правильных"));

        Action overallView = new Action(ActionType.SEE, mountain, constructions);
//        Action
//        мы замечали все большее количество прилепившихся к горным склонам геометрически правильных конструкций
         return this.doAction(this, overallView);
    }

    public Object rememberPaintings()
    {
        Thing paintings = new Thing("картины Николая Рериха из его азиатской серии", "загадочные");

        Action rememberPaintings = new Action(ActionType.REMEMBER, paintings);
         return this.doAction(this, rememberPaintings);
    }

    public Object think()
    {
        Thing constructions = new Thing("конструкции из отдельных гигантских глыб", "эти");

        Action think = new Action(ActionType.THINK, constructions);

        return this.doAction(this, think);
    }

}
