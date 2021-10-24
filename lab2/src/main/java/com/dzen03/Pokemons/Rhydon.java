package com.dzen03.Pokemons;

import com.dzen03.Moves.FurySwipes;

public class Rhydon extends Rhyhorn{
    public Rhydon(String name, int lvl)
    {
        super(name, lvl);
        setStats(105, 130, 120, 45, 45, 40);
        addMove(new FurySwipes());
    }
}
