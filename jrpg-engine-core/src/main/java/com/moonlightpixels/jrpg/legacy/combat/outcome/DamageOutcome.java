package com.moonlightpixels.jrpg.legacy.combat.outcome;

import com.moonlightpixels.jrpg.legacy.combat.Combatant;

public final class DamageOutcome extends CombatOutcome {
    private final int damage;

    public DamageOutcome(final Combatant target, final int damage) {
        super(target);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String getDesciption() {
        return String.format("%s takes %d damage.", getTarget().getName(), damage);
    }
}
