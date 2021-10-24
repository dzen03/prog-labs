package com.dzen03.Moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade()
    {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon enemy, double v) {
        Status enStat = enemy.getCondition();
        if (enStat.equals(Status.BURN) || enStat.equals(Status.POISON) || enStat.equals(Status.PARALYZE))
        {
            super.applyOppDamage(enemy, v * 2);
        }
        else
        {
            super.applyOppDamage(enemy, v);
        }
    }

    @Override
    protected String describe() {
        return "uses Facade";
    }
}
