package com.dzen03.Pokemons;

import ru.ifmo.se.pokemon.*;
import com.dzen03.Moves.*;

public class Snubbull extends Pokemon {
    public Snubbull(String name, int lvl)
    {
        super(name, lvl);
        setType(Type.FAIRY);
        setStats(60, 80, 50, 40, 40, 30);
        setMove(new Confide(), new Facade(), new DoubleTeam());
    }
}
