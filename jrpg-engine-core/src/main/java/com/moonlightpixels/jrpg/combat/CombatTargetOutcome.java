package com.moonlightpixels.jrpg.combat;

import lombok.Data;

@Data
abstract class CombatTargetOutcome {
    private final Combatant target;

    CombatTargetOutcome(final Combatant target) {
        this.target = target;
    }

    abstract void apply();
}
