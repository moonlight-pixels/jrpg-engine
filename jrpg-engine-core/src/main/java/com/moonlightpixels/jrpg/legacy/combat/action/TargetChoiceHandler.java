package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.Combatant;

import java.util.List;

public abstract class TargetChoiceHandler {
    private final TargetChoiceProvider provider;

    protected TargetChoiceHandler(final TargetChoiceProvider provider) {
        this.provider = provider;
    }

    public abstract void start(Battle battle);

    protected final void provideTargets(final List<Combatant> targets) {
        provider.setTargets(targets);
    }
}
