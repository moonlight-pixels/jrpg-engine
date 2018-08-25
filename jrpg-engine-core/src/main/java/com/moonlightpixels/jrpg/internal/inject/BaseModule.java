package com.moonlightpixels.jrpg.internal.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.internal.ConfigurationHandler;
import com.moonlightpixels.jrpg.config.internal.DefaultJRPGConfiguration;
import com.moonlightpixels.jrpg.internal.DefaultJRPGEngine;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;
import com.moonlightpixels.jrpg.internal.launch.ServiceLoaderGameLauncherFactory;

public final class BaseModule extends AbstractModule {
    private final DefaultJRPGConfiguration configuration;

    public BaseModule(final DefaultJRPGConfiguration configuration) {
        configuration.validate();
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(ConfigurationHandler.class).toInstance(configuration);
        bind(JRPGConfiguration.class).toInstance(configuration);
        bind(GameLauncherFactory.class).to(ServiceLoaderGameLauncherFactory.class);
        bind(GdxFacade.class).to(DefaultGdxFacade.class);
        bind(GdxAIFacade.class).to(DefaultGdxAIFacade.class);
        bind(JRPGEngine.class).to(DefaultJRPGEngine.class);
    }

    @Provides
    public GraphicsInitializer provideGraphicsInitializer() {
        return config -> {
            InjectionContext.addModule(new GraphicsModule(
                config.getLaunchConfig().getResolutionWidth(),
                config.getLaunchConfig().getResolutionHeight()
            ));
        };
    }

    @Provides
    public GameInitializer provideGameInitializer() {
        return config -> {
            InjectionContext.addModule(new GameModule(config));
        };
    }
}
