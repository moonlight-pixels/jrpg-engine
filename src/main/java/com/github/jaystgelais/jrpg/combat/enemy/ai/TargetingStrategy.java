package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.github.jaystgelais.jrpg.combat.Combatant;

import java.util.List;

public interface TargetingStrategy {
    Combatant chooseTarget(List<Combatant> targets);
}
