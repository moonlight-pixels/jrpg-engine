package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.action.ActionTypeProvider;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;
import com.github.jaystgelais.jrpg.combat.enemy.Enemy;

public interface EnemyAI {
    void handle(Enemy enemy, ActionTypeProvider provider, Battle battle);

    void handle(Enemy enemy, TargetableChoiceProvider provider, Battle battle);

    void handle(Enemy enemy, TargetChoiceProvider provider, Battle battle);
}
