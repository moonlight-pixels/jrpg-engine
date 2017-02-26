package com.github.jaystgelais.jrpg;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.StackedStateMachine;

import java.time.Clock;
import java.util.Set;

public class Game implements ApplicationListener {
    private final StackedStateMachine gameModes;
    private final GraphicsService graphicsService;
    private final InputService inputService;
    private final Clock clock;
    private long lastRenderTimestampMs;
    private long pauseTimeMs;

    public Game(final Set<GameMode> gameModes, final String initialGameMode, final GraphicsService graphicsService,
                final InputService inputService) {
        this(gameModes, initialGameMode, graphicsService, inputService, Clock.systemUTC());
    }

    Game(final Set<GameMode> gameModes, final String initialGameMode, final GraphicsService graphicsService,
         final InputService inputService, final Clock clock) {
        for (GameMode gameMode : gameModes) {
            gameMode.setGame(this);
        }
        this.gameModes = new StackedStateMachine(gameModes, initialGameMode);
        this.graphicsService = graphicsService;
        this.inputService = inputService;
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
        gameModes.handleInput(inputService);

        graphicsService.clearScreen();
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
    }

    @Override
    public final void resize(final int width, final int height) {
        graphicsService.resize(width, height);
    }

    public final void exitGame() {
        Gdx.app.exit();
    }

    private long updateRenderTimeAndGetTimeElapsed() {
        long previousRenderTime = lastRenderTimestampMs;
        lastRenderTimestampMs = clock.millis();
        return lastRenderTimestampMs - previousRenderTime;
    }

    public final GraphicsService getGraphicsService() {
        return graphicsService;
    }
}
