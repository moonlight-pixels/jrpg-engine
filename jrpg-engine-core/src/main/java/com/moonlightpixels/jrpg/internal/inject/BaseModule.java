package com.moonlightpixels.jrpg.internal.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.internal.DefaultJRPGEngine;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;
import com.moonlightpixels.jrpg.internal.launch.ServiceLoaderGameLauncherFactory;

public final class BaseModule extends AbstractModule {
    private final JRPGConfiguration configuration;

    public BaseModule(final JRPGConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(JRPGConfiguration.class).toInstance(configuration);
        bind(GameLauncherFactory.class).to(ServiceLoaderGameLauncherFactory.class);
        bind(GdxFacade.class).to(DefaultGdxFacade.class);
        bind(GdxAIFacade.class).to(DefaultGdxAIFacade.class);
        bind(JRPGEngine.class).to(DefaultJRPGEngine.class);
        bind(Module.class)
            .annotatedWith(Names.named("Graphics"))
            .toInstance(new GraphicsModule(
                configuration.getLaunchConfig().getResolutionWidth(),
                configuration.getLaunchConfig().getResolutionHeight()
            ));
        bind(Module.class)
            .annotatedWith(Names.named("Game"))
            .toInstance(new GameModule(configuration));
    }
}
