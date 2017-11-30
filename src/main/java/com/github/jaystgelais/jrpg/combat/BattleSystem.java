package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.render.CombatLayout;

public final class BattleSystem {
    private final long turnLengthMs;
    private final CombatLayout combatLayout;

    public BattleSystem(final long turnLengthMs, final CombatLayout combatLayout) {
        this.turnLengthMs = turnLengthMs;
        this.combatLayout = combatLayout;
    }

    public long getTurnLengthMs() {
        return turnLengthMs;
    }

    public CombatLayout getCombatLayout() {
        return combatLayout;
    }
}
