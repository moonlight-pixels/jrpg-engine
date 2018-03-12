package com.github.jaystgelais.jrpg.combat.event;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.state.Updatable;

public abstract class CombatEvent implements Updatable, Comparable<CombatEvent> {
    public static final int TICKS_REMAINING_IMMEDIATE = -1;
    private int ticksRemaining;

    public CombatEvent(final int ticksRemaining) {
        this.ticksRemaining = ticksRemaining;
    }

    public abstract void start(final Battle battle);

    public abstract boolean isComplete();

    public final void tick() {
        ticksRemaining--;
    }

    public final boolean canStart() {
        return ticksRemaining <= 0;
    }

    public final int getTicksRemaining() {
        return ticksRemaining;
    }

    @Override
    public final int compareTo(final CombatEvent other) {
        return Integer.compare(ticksRemaining, other.ticksRemaining);
    }
}
