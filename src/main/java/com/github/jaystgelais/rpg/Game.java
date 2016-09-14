package com.github.jaystgelais.rpg;

import com.badlogic.gdx.ApplicationAdapter;
import com.github.jaystgelais.jrpg.state.StackedStateMachine;

import java.time.Clock;

public class Game extends ApplicationAdapter {
    private StackedStateMachine gameModes;
    private Clock clock;
    private long lastRenderTimestampMs;

    public Game(final StackedStateMachine gameModes) {
        this(gameModes, Clock.systemUTC());
    }

    Game(final StackedStateMachine gameModes, final Clock clock) {
        this.gameModes = gameModes;
        this.clock = clock;
        lastRenderTimestampMs = this.clock.millis();
    }

    @Override
    public final void render() {
        long timeElapsed = updateRenderTimeAndGetTimeElapsed();
        gameModes.update(timeElapsed);
        gameModes.render();
    }

    private long updateRenderTimeAndGetTimeElapsed() {
        long previousRenderTime = lastRenderTimestampMs;
        lastRenderTimestampMs = clock.millis();
        return lastRenderTimestampMs - previousRenderTime;
    }
}
