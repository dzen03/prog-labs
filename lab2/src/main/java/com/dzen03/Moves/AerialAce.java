package com.dzen03.Moves;

import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove {
    public AerialAce()
    {
        super(Type.FLYING, 60, Double.MAX_VALUE);
    }

    @Override
    protected String describe() {
        return "uses Aerial Ace";
    }
}
