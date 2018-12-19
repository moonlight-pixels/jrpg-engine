package com.moonlightpixels.jrpg.combat;

import lombok.Data;

@Data
abstract class CombatActionOutcome {
    private final CombatAction.Key action;
    private final Combatant combatant;

    CombatActionOutcome(final CombatAction.Key action, final Combatant combatant) {
        this.action = action;
        this.combatant = combatant;
    }

    abstract void apply();
}
