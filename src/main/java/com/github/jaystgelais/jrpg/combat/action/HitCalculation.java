package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Combatant;

public interface HitCalculation {
    boolean didHit(final Combatant attacker, final Combatant target);
}
