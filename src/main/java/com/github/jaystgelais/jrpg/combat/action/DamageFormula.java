package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Combatant;

public interface DamageFormula {
    int calculateDamage(final Combatant attacker, final Combatant target);
}
