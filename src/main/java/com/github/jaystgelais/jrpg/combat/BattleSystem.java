package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.state.Updatable;

public abstract class BattleSystem implements PlayerInputHandler, Updatable, Renderable, InputHandler {
    private final long turnLengthMs;

    public BattleSystem(final long turnLengthMs) {
        this.turnLengthMs = turnLengthMs;
    }

    final long getTurnLengthMs() {
        return turnLengthMs;
    }

    public abstract void configureBattle(Battle battle);

    public abstract void registerEncounter(Encounter encounter);
}
