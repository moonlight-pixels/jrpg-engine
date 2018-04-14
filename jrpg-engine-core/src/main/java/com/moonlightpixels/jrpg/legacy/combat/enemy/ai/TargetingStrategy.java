package com.moonlightpixels.jrpg.legacy.combat.enemy.ai;

import com.moonlightpixels.jrpg.legacy.combat.Combatant;

import java.util.List;

public interface TargetingStrategy {
    Combatant chooseTarget(List<? extends Combatant> targets);
}
