package com.moonlightpixels.jrpg.combat;

final class TargetMissOutcome extends CombatTargetOutcome {
    TargetMissOutcome(final Combatant target) {
        super(target);
    }

    @Override
    void apply() { }
}
