package com.github.jaystgelais.jrpg.combat.action;

import com.google.common.base.Preconditions;

import java.util.Optional;

public final class ActionType {
    private final String name;
    private final CombatTargetingData targetingData;
    private final ActionEffect effect;

    public ActionType(final String name, final ActionEffect effect, final CombatTargetingData targetingData) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(effect);
        this.name = name;
        this.effect = effect;
        this.targetingData = targetingData;
    }

    public ActionType(final String name, final ActionEffect effect) {
        this(name, effect, null);
    }

    public String getName() {
        return name;
    }

    public ActionEffect getEffect() {
        return effect;
    }

    public Optional<CombatTargetingData> getTargetingData() {
        return Optional.ofNullable(targetingData);
    }
}
