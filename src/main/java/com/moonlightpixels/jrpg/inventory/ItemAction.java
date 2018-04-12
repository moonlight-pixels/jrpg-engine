package com.moonlightpixels.jrpg.inventory;

import com.moonlightpixels.jrpg.combat.Combatant;
import com.moonlightpixels.jrpg.combat.outcome.CombatOutcome;

import java.util.List;

public interface ItemAction {
    List<CombatOutcome> perform(Combatant target);
}
