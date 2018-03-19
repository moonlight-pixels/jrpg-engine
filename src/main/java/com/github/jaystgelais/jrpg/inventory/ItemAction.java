package com.github.jaystgelais.jrpg.inventory;

import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.combat.outcome.CombatOutcome;

import java.util.List;

public interface ItemAction {
    List<CombatOutcome> perform(Combatant target);
}
