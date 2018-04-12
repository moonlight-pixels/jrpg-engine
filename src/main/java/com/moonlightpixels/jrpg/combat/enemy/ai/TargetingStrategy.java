package com.moonlightpixels.jrpg.combat.enemy.ai;

import com.moonlightpixels.jrpg.combat.Combatant;

import java.util.List;

public interface TargetingStrategy {
    Combatant chooseTarget(List<? extends Combatant> targets);
}
