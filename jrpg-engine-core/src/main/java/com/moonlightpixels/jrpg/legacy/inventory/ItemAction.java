package com.moonlightpixels.jrpg.legacy.inventory;

import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;

import java.util.List;

public interface ItemAction {
    List<CombatOutcome> perform(Combatant target);
}
