package com.moonlightpixels.jrpg.combat.outcome;

import com.moonlightpixels.jrpg.combat.Combatant;

public class DodgeOutcome extends CombatOutcome {
    public DodgeOutcome(final Combatant target) {
        super(target);
    }

    @Override
    public final String getDesciption() {
        return String.format("%s dodges.", getTarget().getName());
    }
}
