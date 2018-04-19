package com.moonlightpixels.jrpg.internal.inject

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.google.inject.AbstractModule
import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory

class TestModule extends AbstractModule {
    JRPGConfiguration jrpgConfiguration
    GameLauncherFactory launcherFactory
    GdxFacade gdx
    GdxAIFacade gdxAI
    AssetManager assetManager
    SpriteBatch spriteBatch
    Camera camera
    Viewport viewport

    @Override
    protected void configure() {
        bind(JRPGConfiguration).toInstance(jrpgConfiguration)
        bind(GameLauncherFactory).toInstance(launcherFactory)
        bind(GdxFacade).toInstance(gdx)
        bind(GdxAIFacade).toInstance(gdxAI)
        bind(AssetManager).toInstance(assetManager)
        bind(SpriteBatch).toInstance(spriteBatch)
        bind(Camera).toInstance(camera)
        bind(Viewport).toInstance(viewport)
    }
}
