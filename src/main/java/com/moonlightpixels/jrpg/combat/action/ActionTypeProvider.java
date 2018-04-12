package com.moonlightpixels.jrpg.combat.action;

public class ActionTypeProvider {
    private CombatActionType actionType;

    public final boolean isComplete() {
        return actionType != null;
    }

    public final CombatActionType getActionType() {
        return actionType;
    }

    public final void setActionType(final CombatActionType actionType) {
        this.actionType = actionType;
    }
}
