package com.github.jaystgelais.jrpg.combat.stats;

public final class StatModifier {
    public enum ModifierAction { ADD, MULTIPLY };

    private final Class<? extends Stat> targetStat;
    private final ModifierAction action;
    private final float modifier;

    public StatModifier(final Class<? extends Stat> targetStat, final ModifierAction action, final float modifier) {
        this.targetStat = targetStat;
        this.action = action;
        this.modifier = modifier;
    }

    public Class<? extends Stat> getTargetStat() {
        return targetStat;
    }

    public ModifierAction getAction() {
        return action;
    }

    public float getModifier() {
        return modifier;
    }
}
