package com.github.jaystgelais.jrpg;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.github.jaystgelais.jrpg.combat.CombatMode;
import com.github.jaystgelais.jrpg.devtools.ScreenShotUtil;
import com.github.jaystgelais.jrpg.frontend.FrontEndMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.menu.MenuMode;
import com.github.jaystgelais.jrpg.state.StackedStateMachine;

import javax.inject.Inject;
import java.time.Clock;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class Game implements ApplicationListener {
    private static final String INITIAL_GAME_MODE = "frontEnd";
    private static Game instance;

    private final StackedStateMachine gameModes;
    private final GraphicsService graphicsService;
    private final InputService inputService;
    private final Clock clock;
    private long lastRenderTimestampMs;
    private long pauseTimeMs;
    private boolean debug = false;

    private final DelayedInput screenShotInput = new DelayedInput(Inputs.SCREENSHOT);

    @Inject
    public Game(final FrontEndMode frontEndMode, final MapMode mapMode, final CombatMode combatMode,
                final MenuMode menuMode, final GraphicsService graphicsService, final InputService inputService,
                final Clock clock) {
        final HashSet<GameMode> gameModes = new HashSet<>(Arrays.asList(frontEndMode, mapMode, combatMode, menuMode));
        for (GameMode gameMode : gameModes) {
            gameMode.setGame(this);
        }
        this.gameModes = new StackedStateMachine(gameModes, INITIAL_GAME_MODE);
        this.graphicsService = graphicsService;
        this.inputService = inputService;
        this.clock = clock;
        lastRenderTimestampMs = this.clock.millis();
    }

    public static Game getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "A Game instance must be constructed and started before the global instance is available."
            );
        }
        return instance;
    }

    private static void registerInstance(final Game game) {
        instance = game;
    }

    public final void start() {
        new LwjglApplication(this, graphicsService.getConfiguration());
        registerInstance(this);
    }

    @Override
    public final void render() {
        long timeElapsed = updateRenderTimeAndGetTimeElapsed();
        gameModes.update(timeElapsed);

        if (isDebug()) {
            handleDebugInput(inputService);
        }
        gameModes.handleInput(inputService);

        graphicsService.clearScreen();
        gameModes.render(graphicsService);
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

    public final boolean isDebug() {
        return debug;
    }

    public final void setDebug(final boolean debug) {
        this.debug = debug;
    }

    public final void exitCurrentGameMode() {
        gameModes.exitCurrentState();
    }

    public final boolean hasGameMode(final String stateKey) {
        return gameModes.hasState(stateKey);
    }

    public final void activateGameMode(final String stateKey, final Map<String, Object> params) {
        gameModes.change(stateKey, params);
    }

    public final void activateGameMode(final String stateKey) {
        gameModes.change(stateKey);
    }

    public final GameMode getActiveMode() {
        return (GameMode) gameModes.getCurrentState();
    }

    private void handleDebugInput(final InputService inputService) {
        if (screenShotInput.isPressed(inputService)) {
            ScreenShotUtil.takeScreenShot();
        }
    }
}
