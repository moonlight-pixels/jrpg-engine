package com.github.jaystgelais.jrpg;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.state.StackedStateMachine;

import java.time.Clock;
import java.util.Collections;

public class Game implements ApplicationListener {
    private final StackedStateMachine gameModes;
    private final GraphicsService graphicsService;
    private final Clock clock;
    private long lastRenderTimestampMs;
    private long pauseTimeMs;

    public Game(final StackedStateMachine gameModes, final GraphicsService graphicsService) {
        this(gameModes, graphicsService, Clock.systemUTC());
    }

    Game(final StackedStateMachine gameModes, final GraphicsService graphicsService, final Clock clock) {
        this.gameModes = gameModes;
        this.graphicsService = graphicsService;
        this.clock = clock;
        lastRenderTimestampMs = this.clock.millis();
    }

    public final void start() {
        new LwjglApplication(this, graphicsService.getConfiguration());
    }

    @Override
    public final void render() {
        long timeElapsed = updateRenderTimeAndGetTimeElapsed();
        gameModes.update(timeElapsed);

        graphicsService.renderStart();
        gameModes.render(graphicsService);
        graphicsService.renderEnd();
    }

    @Override
    public final void pause() {
        pauseTimeMs = clock.millis();
    }

    @Override
    public final void resume() {
        lastRenderTimestampMs = clock.millis() - (pauseTimeMs - lastRenderTimestampMs);
    }

    @Override
    public final void dispose() {
        gameModes.dispose();
        graphicsService.dispose();
    }

    @Override
    public final void create() {
        graphicsService.init();
        gameModes.getCurrentState().onEnter(Collections.emptyMap());
    }

    @Override
    public final void resize(final int width, final int height) {
        graphicsService.resize(width, height);
    }

    private long updateRenderTimeAndGetTimeElapsed() {
        long previousRenderTime = lastRenderTimestampMs;
        lastRenderTimestampMs = clock.millis();
        return lastRenderTimestampMs - previousRenderTime;
    }
}
