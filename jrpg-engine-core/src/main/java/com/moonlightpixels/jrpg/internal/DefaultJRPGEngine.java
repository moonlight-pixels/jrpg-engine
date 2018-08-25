package com.moonlightpixels.jrpg.internal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.inject.GameInitializer;
import com.moonlightpixels.jrpg.internal.inject.GraphicsInitializer;
import com.moonlightpixels.jrpg.internal.inject.InjectionContext;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;

import javax.inject.Inject;

public final class DefaultJRPGEngine extends ApplicationAdapter implements JRPGEngine {
    private final JRPGConfiguration configuration;
    private final GameLauncherFactory gameLauncherFactory;
    private final GdxFacade gdx;
    private final GdxAIFacade gdxAI;
    private final GraphicsInitializer graphicsInitializer;
    private final GameInitializer gameInitializer;
    private JRPG jrpg;

    @Inject
    public DefaultJRPGEngine(final JRPGConfiguration configuration, final GameLauncherFactory gameLauncherFactory,
                             final GdxFacade gdx, final GdxAIFacade gdxAI,
                             final GraphicsInitializer graphicsInitializer, final GameInitializer gameInitializer) {
        this.configuration = configuration;
        this.gameLauncherFactory = gameLauncherFactory;
        this.gdx = gdx;
        this.gdxAI = gdxAI;
        this.graphicsInitializer = graphicsInitializer;
        this.gameInitializer = gameInitializer;
    }

    @Override
    public void run() {
        gameLauncherFactory
            .getGameLauncher()
            .launch(this, configuration.getLaunchConfig());
    }

    @Override
    public void create() {
        graphicsInitializer.initialize(configuration);
        gameInitializer.initialize(configuration);
        if (configuration.getLaunchConfig().isFullscreen()) {
            Graphics.Monitor monitor = gdx.getGraphics().getMonitor();
            Graphics.DisplayMode displayMode = gdx.getGraphics().getDisplayMode(monitor);
            gdx.getGraphics().setFullscreenMode(displayMode);
        }
        jrpg = InjectionContext.get().getInstance(JRPG.class);
        jrpg.init();
    }

    @Override
    public void render() {
        if (gdx.getInput().isKeyPressed(Input.Keys.ESCAPE)) {
            gdx.getApp().exit();
            return;
        }
        gdxAI.getTimepiece().update(gdx.getGraphics().getDeltaTime());
        clearScreen();
        jrpg.update();
    }

    private void clearScreen() {
        gdx.getGl().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        InjectionContext.get().getInstance(AssetManager.class).dispose();
    }
}
