package com.moonlightpixels.jrpg.internal.inject;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.internal.DefaultUiStyleSupplier;

import javax.inject.Singleton;

public class GraphicsModule extends AbstractModule {
    private final int resolutionWidth;
    private final int resolutionHeight;

    public GraphicsModule(final int resolutionWidth, final int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
    }

    @Override
    protected final void configure() {
        bind(AssetManager.class).asEagerSingleton();
        bind(SpriteBatch.class).asEagerSingleton();
        bind(Camera.class).toInstance(new OrthographicCamera(resolutionWidth, resolutionHeight));
    }

    @Provides
    @Singleton
    public final Viewport provideViewport(final Camera camera) {
        return new FitViewport(resolutionWidth, resolutionHeight, camera);
    }

    @Provides
    @Singleton
    public final UiStyle provideUiStyle(final JRPGConfiguration jrpgConfiguration) {
        UiStyle uiStyle = new DefaultUiStyleSupplier().get();
        jrpgConfiguration.configure(uiStyle);

        return uiStyle;
    }
}
