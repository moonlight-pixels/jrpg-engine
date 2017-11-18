package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.Combatant;

public interface ActionEffect {
    void apply(final Battle battle, final Combatant performer, final Combatant target);
}
