package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.Optional;

public abstract class CombatEvent implements Updatable, Comparable<CombatEvent> {
    public static final int TICKS_REMAINING_IMMEDIATE = -1;
    private int ticksRemaining;
    private final Combatant owner;

    protected CombatEvent(final int ticksRemaining, final Combatant owner) {
        this.ticksRemaining = ticksRemaining;
        this.owner = owner;
    }

    protected CombatEvent(final int ticksRemaining) {
        this(ticksRemaining, null);
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

    public final Optional<Combatant> getOwner() {
        return Optional.ofNullable(owner);
    }

    @Override
    public final int compareTo(final CombatEvent other) {
        return Integer.compare(ticksRemaining, other.ticksRemaining);
    }
}
