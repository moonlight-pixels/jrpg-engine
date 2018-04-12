package com.moonlightpixels.jrpg.combat.enemy.ai;

import com.moonlightpixels.jrpg.combat.Battle;
import com.moonlightpixels.jrpg.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.combat.enemy.Enemy;

public interface EnemyAI {
    void handle(Enemy enemy, ActionTypeProvider provider, Battle battle);

    void handle(Enemy enemy, TargetableChoiceProvider provider, Battle battle);

    void handle(Enemy enemy, TargetChoiceProvider provider, Battle battle);
}
