package com.dzen03.subjects;

import com.dzen03.core.*;


public class This extends Subject {
    public This()
    {
        super(FaceType.IT, "это");
    }

    public Object be_like()
    {
        Thing ruins = new Thing("на развалины Мачу Пикчу в Андах", "похоже");
        Thing walls = new Thing("стены Киша",
                "обнаруженные археологической экспедицией Оксфордского музея", "под открытым небом",
                "крепостные");

        Action be_like = new Action(ActionType.BE, ruins, walls);

        return this.doAction(this, be_like);
    }
}
