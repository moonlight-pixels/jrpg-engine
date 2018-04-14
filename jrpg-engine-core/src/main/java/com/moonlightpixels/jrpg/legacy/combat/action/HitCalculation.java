package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Combatant;

public interface HitCalculation {
    boolean didHit(final Combatant attacker, final Combatant target);
}
