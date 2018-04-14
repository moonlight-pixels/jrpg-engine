package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Combatant;

public interface DamageFormula {
    int calculateDamage(final Combatant attacker, final Combatant target);
}
