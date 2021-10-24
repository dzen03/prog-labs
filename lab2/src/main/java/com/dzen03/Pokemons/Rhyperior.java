package com.dzen03.Pokemons;

import com.dzen03.Moves.ShadowBall;

public class Rhyperior extends Rhydon{
    public Rhyperior(String name, int lvl)
    {
        super(name, lvl);
        setStats(115, 140, 130, 55, 55, 40);
        addMove(new ShadowBall());
    }
}
