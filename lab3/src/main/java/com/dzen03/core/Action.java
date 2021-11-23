package com.dzen03.core;

import java.util.Arrays;
import java.util.Objects;

public class Action {
    private Thing[] things;
    private ActionType type;

    public Action(ActionType type, Thing... descriptions)
    {
        this.things = descriptions;
        this.type = type;
    }

//    public Action(ActionType[] types, Thing... descriptions)
//    {
//        this.things = descriptions;
//        this.type = type;
//    }

    public Action(ActionType type, String description) {
        this(type, new Thing(description));
    }

    public Thing[] getThings()
    {
        return things;
    }

    public String getAction(FaceType face) {
        return this.getAction(face, true, false);
    }

    public String getAction(FaceType face, boolean fullStop, boolean negative) {
        StringBuilder result = new StringBuilder();
        for (Thing thing: things)
        {
            result.append(thing.toString()).append(", ");
        }
        if (result.length() > 3)
            result.delete(result.length() - 2, result.length());
        return " " + type.getVerb(face) + result + (fullStop ? "." : "");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Arrays.equals(things, action.things);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) things);
    }
}
