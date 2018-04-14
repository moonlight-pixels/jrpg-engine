package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;

import java.util.LinkedList;
import java.util.List;

public abstract class ActionRenderer {
    private final List<CombatOutcome> outcomes = new LinkedList<>();

    public abstract void start(final Battle battle);

    public abstract boolean isComplete();

    public final void registerOutcome(final CombatOutcome outcome) {
        outcomes.add(outcome);
    }

    protected final List<CombatOutcome> getOutcomes() {
        return outcomes;
    }
}
