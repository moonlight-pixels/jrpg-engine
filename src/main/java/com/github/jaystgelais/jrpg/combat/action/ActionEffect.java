package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Combatant;

public interface ActionEffect {
    void apply(final Combatant performer, final Combatant target);
}
