package com.github.jaystgelais.jrpg.combat.stats;

public class MaxHP extends Stat {

    public MaxHP(final int baseValue) {
        super(baseValue);
    }

    @Override
    public String getName() {
        return "Max HP";
    }
}
