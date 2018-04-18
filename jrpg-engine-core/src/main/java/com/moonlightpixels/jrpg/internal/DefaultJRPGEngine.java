package com.moonlightpixels.jrpg.internal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Graphics;
import com.google.inject.Injector;
import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;

public final class DefaultJRPGEngine implements JRPGEngine, ApplicationListener {
    private final Injector injector;
    private final JRPGConfiguration configuration;
    private final GameLauncherFactory gameLauncherFactory;
    private final GdxFacade gdx;
    private final GdxAIFacade gdxAI;

    public DefaultJRPGEngine(final Injector injector) {
        this.injector = injector;
        this.configuration = injector.getInstance(JRPGConfiguration.class);
        this.gameLauncherFactory = injector.getInstance(GameLauncherFactory.class);
        this.gdx = injector.getInstance(GdxFacade.class);
        this.gdxAI = injector.getInstance(GdxAIFacade.class);
    }

    @Override
    public void run() {
        gameLauncherFactory
            .getGameLauncher()
            .launch(this, configuration.getLaunchConfig());
    }

    @Override
    public void create() {
        if (configuration.getLaunchConfig().isFullscreen()) {
            Graphics.Monitor monitor = gdx.getGraphics().getMonitor();
            Graphics.DisplayMode displayMode = gdx.getGraphics().getDisplayMode(monitor);
            gdx.getGraphics().setFullscreenMode(displayMode);
        }
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void render() {
        gdxAI.getTimepiece().update(gdx.getGraphics().getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
