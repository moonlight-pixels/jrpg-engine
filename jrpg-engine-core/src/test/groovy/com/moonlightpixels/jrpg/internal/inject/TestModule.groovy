package com.moonlightpixels.jrpg.internal.inject

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.config.internal.DefaultJRPGConfiguration
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory

class TestModule extends AbstractModule {
    JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration(
        launchConfig: LaunchConfig.builder()
            .resolutionWidth(640)
            .resolutionHeight(480)
            .fullscreen(true)
            .build()
    )
    GameLauncherFactory gameLauncherFactory
    GdxFacade gdxFacade
    GdxAIFacade gdxAIFacade

    @Provides
    JRPGConfiguration provideJRPGConfiguration() {
        jrpgConfiguration
    }

    @Provides
    GameLauncherFactory provideGameLauncherFactory() {
        gameLauncherFactory
    }

    @Provides
    GdxFacade provideGdxFacade() {
        gdxFacade
    }

    @Provides
    GdxAIFacade provideGdxAIFacade() {
        gdxAIFacade
    }
}
