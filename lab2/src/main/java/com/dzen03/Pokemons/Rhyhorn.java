package com.dzen03.Pokemons;

import ru.ifmo.se.pokemon.*;
import com.dzen03.Moves.*;

public class Rhyhorn extends Pokemon {
    public Rhyhorn(String name, int lvl)
    {
        super(name, lvl);
        setType(Type.GROUND, Type.ROCK);
        setStats(80, 85, 95, 30, 30, 25);
        setMove(new Facade(), new AerialAce());
    }
}
