package com.github.jaystgelais.jrpg.combat.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Stat {
    private int baseValue;
    private final Map<String, StatModifier> modifiers = new HashMap<>();

    public Stat(final int baseValue) {
        this.baseValue = baseValue;
    }

    public abstract String getName();

    public final int getBaseValue() {
        return baseValue;
    }

    public final void setBaseValue(final int baseValue) {
        this.baseValue = baseValue;
    }

    public final int getValue() {
        AtomicReference<Float> value = new AtomicReference<>((float) baseValue);

        modifiers
                .values()
                .stream()
                .filter(statModifier -> statModifier.getAction() == StatModifier.ModifierAction.ADD)
                .forEach(statModifier -> value.set(value.get() + statModifier.getModifier()));

        modifiers
                .values()
                .stream()
                .filter(statModifier -> statModifier.getAction() == StatModifier.ModifierAction.MULTIPLY)
                .forEach(statModifier -> value.set(value.get() * statModifier.getModifier()));

        return Math.round(value.get());
    }

    public final void addModifier(final String id, final StatModifier modifier) {
        modifiers.put(id, modifier);
    }

    public final void removeModifier(final String id) {
        modifiers.remove(id);
    }
}
