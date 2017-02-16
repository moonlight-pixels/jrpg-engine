package com.github.jaystgelais.jrpg;

import com.badlogic.gdx.ApplicationAdapter;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.state.StackedStateMachine;

import java.time.Clock;

public class Game extends ApplicationAdapter {
    private final StackedStateMachine gameModes;
    private final GraphicsService graphicsService;
    private final Clock clock;
    private long lastRenderTimestampMs;

    public Game(final StackedStateMachine gameModes, final GraphicsService graphicsService) {
        this(gameModes, graphicsService, Clock.systemUTC());
    }

    Game(final StackedStateMachine gameModes, final GraphicsService graphicsService, final Clock clock) {
        this.gameModes = gameModes;
        this.graphicsService = graphicsService;
        this.clock = clock;
        lastRenderTimestampMs = this.clock.millis();
    }

    @Override
    public final void render() {
        long timeElapsed = updateRenderTimeAndGetTimeElapsed();
        gameModes.update(timeElapsed);
        gameModes.render(graphicsService);
    }

    private long updateRenderTimeAndGetTimeElapsed() {
        long previousRenderTime = lastRenderTimestampMs;
        lastRenderTimestampMs = clock.millis();
        return lastRenderTimestampMs - previousRenderTime;
    }
}
