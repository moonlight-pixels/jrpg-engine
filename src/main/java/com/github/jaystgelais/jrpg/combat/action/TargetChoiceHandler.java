package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.Combatant;

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
