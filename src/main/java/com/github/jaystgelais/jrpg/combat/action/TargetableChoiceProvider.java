package com.github.jaystgelais.jrpg.combat.action;

public class TargetableChoiceProvider<T extends Targetable> {
    private T choice;

    public final boolean isComplete() {
        return choice != null;
    }

    public final T getChoice() {
        return choice;
    }

    public final void setChoice(final T choice) {
        this.choice = choice;
    }
}
