package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Battle;

public abstract class ActionTypeChoiceHandler {
    private final ActionTypeProvider provider;

    protected ActionTypeChoiceHandler(final ActionTypeProvider provider) {
        this.provider = provider;
    }

    public abstract void start(Battle battle);

    protected final void provideChoice(final CombatActionType choice) {
        provider.setActionType(choice);
    }
}
