package com.moonlightpixels.jrpg.legacy.combat.enemy.ai;

import com.moonlightpixels.jrpg.legacy.combat.action.CombatActionType;
import com.moonlightpixels.jrpg.legacy.combat.action.Targetable;

import java.util.Optional;

public final class EnemyAction {
    private final CombatActionType actionType;
    private final Targetable targetable;
    private final TargetingStrategy targetingStrategy;

    public EnemyAction(final CombatActionType actionType, final Targetable targetable,
                       final TargetingStrategy targetingStrategy) {
        this.actionType = actionType;
        this.targetable = targetable;
        this.targetingStrategy = targetingStrategy;
    }

    public EnemyAction(final CombatActionType actionType, final TargetingStrategy targetingStrategy) {
        this(actionType, null, targetingStrategy);
    }

    public CombatActionType getActionType() {
        return actionType;
    }

    public Optional<? extends Targetable> getTargetable() {
        return Optional.ofNullable(targetable);
    }

    public TargetingStrategy getTargetingStrategy() {
        return targetingStrategy;
    }
}
