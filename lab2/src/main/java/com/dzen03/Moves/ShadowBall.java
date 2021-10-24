package com.dzen03.Moves;

import ru.ifmo.se.pokemon.*;

import java.util.Random;

public class ShadowBall extends SpecialMove {
    public ShadowBall()
    {
        super(Type.GHOST, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Random chanceOfLoweringSD = new Random();
        if (chanceOfLoweringSD.nextDouble() < 0.2)
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -1);
    }

    @Override
    protected String describe() {
        return "uses Shadow Ball";
    }
}
