package com.dzen03.Pokemons;

import com.dzen03.Moves.*;
import ru.ifmo.se.pokemon.*;

public class Zangoose extends Pokemon {

    public Zangoose(String name, int lvl) {
        super(name, lvl);
        setStats(73, 115, 60, 60, 60, 90);
        setType(Type.NORMAL);
        setMove(new Swagger(), new Facade(), new DoubleTeam(), new Confide());
    }
}
