package com.moonlightpixels.jrpg.internal.inject;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moonlightpixels.jrpg.config.internal.ConfigurationHandler;
import com.moonlightpixels.jrpg.internal.gdx.factory.DefaultLabelFactory;
import com.moonlightpixels.jrpg.internal.gdx.factory.LabelFactory;
import com.moonlightpixels.jrpg.internal.graphics.DefaultGraphicsContext;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.internal.DefaultUiStyle;
import com.moonlightpixels.jrpg.ui.internal.DefaultUiStyleSupplier;

import javax.inject.Named;
import javax.inject.Singleton;

public final class GraphicsModule extends AbstractModule {
    public static final String FRONTEND = "FrontEnd";
    public static final String MAP = "Map";
    public static final String COMBAT = "Combat";
    public static final String UI = "UI";

    private final int resolutionWidth;
    private final int resolutionHeight;

    GraphicsModule(final int resolutionWidth, final int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
    }

    @Override
    protected void configure() {
        bind(SpriteBatch.class).asEagerSingleton();
        bind(LabelFactory.class).to(DefaultLabelFactory.class);
    }

    @Provides
    @Singleton
    public AssetManager provideAssetManager() {
        final AssetManager assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        return assetManager;
    }

    @Provides
    @Singleton
    @Named(FRONTEND)
    public GraphicsContext provideFrontEndGraphicsContext(final SpriteBatch spriteBatch) {
        return new DefaultGraphicsContext(
            new OrthographicCamera(resolutionWidth, resolutionHeight),
            spriteBatch,
            true
        );
    }

    @Provides
    @Singleton
    @Named(MAP)
    public GraphicsContext provideMapGraphicsContext(final SpriteBatch spriteBatch) {
        return new DefaultGraphicsContext(
            new OrthographicCamera(resolutionWidth, resolutionHeight),
            spriteBatch,
            false
        );
    }

    @Provides
    @Singleton
    @Named(COMBAT)
    public GraphicsContext provideCombatGraphicsContext(final SpriteBatch spriteBatch) {
        return new DefaultGraphicsContext(
            new OrthographicCamera(resolutionWidth, resolutionHeight),
            spriteBatch,
            true
        );
    }

    @Provides
    @Singleton
    @Named(UI)
    public GraphicsContext provideUiGraphicsContext(final SpriteBatch spriteBatch) {
        return new DefaultGraphicsContext(
            new OrthographicCamera(resolutionWidth, resolutionHeight),
            spriteBatch,
            true
        );
    }

    @Provides
    @Singleton
    public UiStyle provideUiStyle(final ConfigurationHandler configurationHandler,
                                  final LabelFactory labelFactory) {
        UiStyle uiStyle = new DefaultUiStyleSupplier(new DefaultUiStyle(labelFactory)).get();
        configurationHandler.configure(uiStyle);

        return uiStyle;
    }
}
