package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.graphics.Renderable;
import com.moonlightpixels.jrpg.input.InputHandler;
import com.moonlightpixels.jrpg.state.Updatable;

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
