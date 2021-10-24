package com.dzen03.Moves;

import ru.ifmo.se.pokemon.*;

import java.util.Random;

public class Blizzard extends SpecialMove {
    public Blizzard()
    {
        super(Type.ICE, 110, 70);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Random chanceOfFreeze = new Random();
        if (chanceOfFreeze.nextDouble() < 0.1)
            Effect.freeze(pokemon);
    }

    @Override
    protected String describe() {
        return "uses Blizzard";
    }
}
