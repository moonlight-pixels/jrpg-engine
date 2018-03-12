package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.outcome.CombatOutcome;

import java.util.LinkedList;
import java.util.List;

public abstract class ActionRenderer {
    private final List<CombatOutcome> outcomes = new LinkedList<>();

    public abstract void start(final Battle battle);

    public abstract boolean isComplete();

    public final void registerOutcome(final CombatOutcome outcome) {
        outcomes.add(outcome);
    }
}
