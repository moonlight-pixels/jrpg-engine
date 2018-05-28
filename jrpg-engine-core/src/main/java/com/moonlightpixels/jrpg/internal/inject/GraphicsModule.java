package com.moonlightpixels.jrpg.internal.inject;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.internal.DefaultUiStyleSupplier;

import javax.inject.Named;
import javax.inject.Singleton;

public final class GraphicsModule extends AbstractModule {
    public static final String MAP_CAMERA = "Map";
    public static final String UI_CAMERA = "UI";

    private final int resolutionWidth;
    private final int resolutionHeight;

    GraphicsModule(final int resolutionWidth, final int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
    }

    @Override
    protected void configure() {
        bind(SpriteBatch.class).asEagerSingleton();
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
    @Named(MAP_CAMERA)
    public OrthographicCamera provideMapCamera() {
        return new OrthographicCamera(resolutionWidth, resolutionHeight);
    }

    @Provides
    @Singleton
    @Named(UI_CAMERA)
    public Camera provideUICamera() {
        return new OrthographicCamera(resolutionWidth, resolutionHeight);
    }

    @Provides
    @Singleton
    public Viewport provideViewport(final Camera camera) {
        return new FitViewport(resolutionWidth, resolutionHeight, camera);
    }

    @Provides
    @Singleton
    public UiStyle provideUiStyle(final JRPGConfiguration jrpgConfiguration) {
        UiStyle uiStyle = new DefaultUiStyleSupplier().get();
        jrpgConfiguration.configure(uiStyle);

        return uiStyle;
    }
}
