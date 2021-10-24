package com.dzen03.Moves;

import ru.ifmo.se.pokemon.*;

import java.util.Random;

public class FurySwipes extends PhysicalMove {
    public FurySwipes()
    {
        super(Type.NORMAL, 18, 80);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        Random rand = new Random();
        double numberOfSwipes = rand.nextDouble();
        if (numberOfSwipes < 3./8)
            super.applyOppDamage(pokemon, v * 2);
        else if (numberOfSwipes < 6./8)
            super.applyOppDamage(pokemon, v * 3);
        else if (numberOfSwipes < 7./8)
            super.applyOppDamage(pokemon, v * 4);
        else
            super.applyOppDamage(pokemon, v * 5);
    }

    @Override
    protected String describe() {
        return "uses Fury Swipes";
    }
}
