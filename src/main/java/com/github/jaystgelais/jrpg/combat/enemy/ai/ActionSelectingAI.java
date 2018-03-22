package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.action.ActionTypeProvider;
import com.github.jaystgelais.jrpg.combat.action.AllowedTargets;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;
import com.github.jaystgelais.jrpg.combat.enemy.Enemy;

public abstract class ActionSelectingAI implements EnemyAI {
    private EnemyAction currentAction;
    private AllowedTargets currentAllowedTargets;

    protected abstract EnemyAction getEnemyAction(Battle battle);

    @Override
    public final void handle(final Enemy enemy, final ActionTypeProvider provider, final Battle battle) {
        currentAction = getEnemyAction(battle);
        provider.setActionType(currentAction.getActionType());
    }

    @Override
    public final void handle(final Enemy enemy, final TargetableChoiceProvider provider, final Battle battle) {
        currentAction.getTargetable().ifPresent(provider::setChoice);
        currentAllowedTargets = provider.getChoice().getAllowedTargets();

    }

    @Override
    public final void handle(final Enemy enemy, final TargetChoiceProvider provider, final Battle battle) {
        provider.setTargets(
                battle
                        .getFixedTargetsForAllowedTargets(enemy, currentAllowedTargets)
                        .orElse(battle.getEligibleTargets(enemy, currentAllowedTargets))
        );
    }
}
