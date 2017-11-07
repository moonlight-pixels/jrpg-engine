package com.github.jaystgelais.jrpg.combat;

public final class BattleSystem {
    private final long turnLengthMs;

    public BattleSystem(final long turnLengthMs) {
        this.turnLengthMs = turnLengthMs;
    }

    public long getTurnLengthMs() {
        return turnLengthMs;
    }
}
