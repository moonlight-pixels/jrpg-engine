package com.moonlightpixels.jrpg.combat.action;

import com.moonlightpixels.jrpg.combat.Combatant;

public interface DamageFormula {
    int calculateDamage(final Combatant attacker, final Combatant target);
}
