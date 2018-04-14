package com.moonlightpixels.jrpg.legacy.inject;

import com.badlogic.gdx.assets.AssetManager;
import com.moonlightpixels.jrpg.legacy.Game;
import com.moonlightpixels.jrpg.legacy.combat.BattleSystem;
import com.moonlightpixels.jrpg.legacy.combat.CombatMode;
import com.moonlightpixels.jrpg.legacy.frontend.FrontEndMode;
import com.moonlightpixels.jrpg.legacy.frontend.NewGameLauncher;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsServiceImpl;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.map.MapMode;
import com.moonlightpixels.jrpg.legacy.ui.menu.MenuDefinition;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.time.Clock;

public abstract class JRPGModule extends AbstractModule {

    @Override
    protected final void configure() {
        bind(Clock.class).toInstance(Clock.systemUTC());
        bind(Game.class);
    }

    @Provides
    @Singleton
    public final AssetManager provideAssetManager() {
        return new AssetManager();
    }

    @Provides
    @Singleton
    public final GraphicsService provideGraphicsService(final AssetManager assetManager) {
        final GraphicsService graphicsService = new GraphicsServiceImpl(assetManager);
        graphicsService.setResolutionWidth(getResolutionWidth());
        graphicsService.setResolutionHeight(getResolutionHeight());
        configureGraphicsService(graphicsService);

        return graphicsService;
    }

    @Provides
    public final InputService provideInputService() {
        return createInputService();
    }

    @Provides
    @Singleton
    public final FrontEndMode providFrontEndMode() {
        return new FrontEndMode(getTitleScreenPath(), getNewGameLauncher());
    }

    @Provides
    @Singleton
    public final MapMode provideMapMode(final AssetManager assetManager) {
        return new MapMode(assetManager, createMainMenu());
    }

    @Provides
    @Singleton
    public final CombatMode providCombatMode() {
        return new CombatMode(createBattleSystem());
    }

    protected void configureGraphicsService(final GraphicsService graphicsService) { }

    protected abstract int getResolutionWidth();

    protected abstract int getResolutionHeight();

    protected abstract InputService createInputService();

    protected abstract String getTitleScreenPath();

    protected abstract NewGameLauncher getNewGameLauncher();

    protected abstract MenuDefinition createMainMenu();

    protected abstract BattleSystem createBattleSystem();
}
