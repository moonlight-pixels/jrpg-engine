package com.github.jaystgelais.jrpg.inject;

import com.badlogic.gdx.assets.AssetManager;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.combat.CombatMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.GraphicsServiceImpl;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.MapDefinition;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.menu.MenuDefinition;
import com.github.jaystgelais.jrpg.menu.MenuMode;
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
    public final MapMode provideMapMode(final AssetManager assetManager) {
        return new MapMode(getInitialMapDefinition(), getInitialLocation(), assetManager);
    }

    @Provides
    @Singleton
    public final CombatMode providCombatMode() {
        return new CombatMode();
    }

    @Provides
    @Singleton
    public final MenuMode providMenuMode() {
        return new MenuMode(createMainMenu());
    }

    protected void configureGraphicsService(final GraphicsService graphicsService) { }

    protected abstract int getResolutionWidth();

    protected abstract int getResolutionHeight();

    protected abstract InputService createInputService();

    protected abstract MapDefinition getInitialMapDefinition();

    protected abstract TileCoordinate getInitialLocation();

    protected abstract MenuDefinition createMainMenu();
}
