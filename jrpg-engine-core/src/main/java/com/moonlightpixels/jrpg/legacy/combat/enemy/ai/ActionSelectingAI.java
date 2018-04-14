package com.moonlightpixels.jrpg.legacy.combat.enemy.ai;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.enemy.Enemy;

import java.util.Collections;

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
                        .orElse(
                                Collections.singletonList(
                                        currentAction
                                                .getTargetingStrategy()
                                                .chooseTarget(battle.getEligibleTargets(enemy, currentAllowedTargets))
                                )
                        )
        );
    }
}
