package com.github.jaystgelais.jrpg.combat.action;

public class TargetableChoiceProvider<T extends Targetable> {
    private final Class<T> type;
    private T choice;

    public TargetableChoiceProvider(final Class<T> type) {
        this.type = type;
    }

    public final boolean isComplete() {
        return choice != null;
    }

    public final T getChoice() {
        return choice;
    }

    public final void setChoice(final T choice) {
        this.choice = choice;
    }

    public final Class<T> getType() {
        return type;
    }
}
