package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.action.ActionTypeProvider;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;

public interface EnemyAI {
    void handle(ActionTypeProvider provider, Battle battle);

    void handle(TargetableChoiceProvider provider, Battle battle);

    void handle(TargetChoiceProvider provider, Battle battle);
}
