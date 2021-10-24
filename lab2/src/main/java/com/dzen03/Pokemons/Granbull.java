package com.dzen03.Pokemons;

import com.dzen03.Moves.*;

public class Granbull extends Snubbull{
    public Granbull(String name, int lvl)
    {
        super(name, lvl);
        setStats(90, 120, 75, 60, 60, 45);
        addMove(new Blizzard());
    }
}
