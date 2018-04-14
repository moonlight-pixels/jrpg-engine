package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.graphics.Renderable;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

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
