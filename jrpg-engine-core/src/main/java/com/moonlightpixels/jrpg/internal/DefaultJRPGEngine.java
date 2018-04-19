package com.moonlightpixels.jrpg.internal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.google.inject.Module;
import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.inject.InjectionContext;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;

import javax.inject.Inject;
import javax.inject.Named;

public final class DefaultJRPGEngine extends ApplicationAdapter implements JRPGEngine {
    private final JRPGConfiguration configuration;
    private final GameLauncherFactory gameLauncherFactory;
    private final GdxFacade gdx;
    private final GdxAIFacade gdxAI;
    private final Module graphicsModule;

    @Inject
    public DefaultJRPGEngine(final JRPGConfiguration configuration, final GameLauncherFactory gameLauncherFactory,
                             final GdxFacade gdx, final GdxAIFacade gdxAI,
                             @Named("Graphics") final Module graphicsModule) {
        this.configuration = configuration;
        this.gameLauncherFactory = gameLauncherFactory;
        this.gdx = gdx;
        this.gdxAI = gdxAI;
        this.graphicsModule = graphicsModule;
    }

    @Override
    public void run() {
        gameLauncherFactory
            .getGameLauncher()
            .launch(this, configuration.getLaunchConfig());
    }

    @Override
    public void create() {
        InjectionContext.addModule(graphicsModule);
        if (configuration.getLaunchConfig().isFullscreen()) {
            Graphics.Monitor monitor = gdx.getGraphics().getMonitor();
            Graphics.DisplayMode displayMode = gdx.getGraphics().getDisplayMode(monitor);
            gdx.getGraphics().setFullscreenMode(displayMode);
        }
    }

    @Override
    public void render() {
        if (gdx.getInput().isKeyPressed(Input.Keys.ESCAPE)) {
            gdx.getApp().exit();
            return;
        }
        gdxAI.getTimepiece().update(gdx.getGraphics().getDeltaTime());
    }

    @Override
    public void dispose() {
        InjectionContext.get().getInstance(AssetManager.class).dispose();
    }
}
