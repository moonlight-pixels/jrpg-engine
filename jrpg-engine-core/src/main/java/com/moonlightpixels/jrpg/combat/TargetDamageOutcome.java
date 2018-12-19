package com.moonlightpixels.jrpg.combat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
final class TargetDamageOutcome extends CombatTargetOutcome {
    private final int damage;

    TargetDamageOutcome(final Combatant target, final int damage) {
        super(target);
        this.damage = damage;
    }

    @Override
    void apply() {
        // TODO Make this work
    }
}
