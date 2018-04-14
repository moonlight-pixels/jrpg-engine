package com.moonlightpixels.jrpg.legacy.combat.enemy.ai;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.enemy.Enemy;

public interface EnemyAI {
    void handle(Enemy enemy, ActionTypeProvider provider, Battle battle);

    void handle(Enemy enemy, TargetableChoiceProvider provider, Battle battle);

    void handle(Enemy enemy, TargetChoiceProvider provider, Battle battle);
}
