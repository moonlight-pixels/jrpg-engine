package com.github.jaystgelais.jrpg.combat.stats;

public final class AttackPower extends Stat {
    public AttackPower(final int baseValue) {
        super(baseValue);
    }

    @Override
    public String getName() {
        return "Attack Power";
    }
}
