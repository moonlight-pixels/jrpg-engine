package com.github.jaystgelais.jrpg.combat.outcome;

import com.github.jaystgelais.jrpg.combat.Combatant;

public abstract class CombatOutcome {
    private final Combatant target;

    public CombatOutcome(final Combatant target) {
        this.target = target;
    }

    public abstract String getDesciption();

    public final Combatant getTarget() {
        return target;
    }
}
