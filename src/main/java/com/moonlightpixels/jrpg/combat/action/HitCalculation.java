package com.moonlightpixels.jrpg.combat.action;

import com.moonlightpixels.jrpg.combat.Combatant;

public interface HitCalculation {
    boolean didHit(final Combatant attacker, final Combatant target);
}
