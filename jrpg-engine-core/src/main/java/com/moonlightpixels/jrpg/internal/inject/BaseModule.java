package com.moonlightpixels.jrpg.internal.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.config.ContentRegistry;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.internal.ConfigurationHandler;
import com.moonlightpixels.jrpg.config.internal.DefaultContentRegistry;
import com.moonlightpixels.jrpg.config.internal.DefaultJRPGConfiguration;
import com.moonlightpixels.jrpg.internal.DefaultJRPGEngine;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;
import com.moonlightpixels.jrpg.internal.launch.ServiceLoaderGameLauncherFactory;
import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry;
import com.moonlightpixels.jrpg.map.internal.MapRegistry;
import com.moonlightpixels.jrpg.ui.standard.internal.DefaultMenuConfiguration;
import com.moonlightpixels.jrpg.ui.standard.internal.MenuConfigurationHandler;

public final class BaseModule extends AbstractModule {
    private final DefaultJRPGConfiguration configuration;
    private final DefaultMenuConfiguration menuConfiguration;

    public BaseModule(final DefaultJRPGConfiguration configuration) {
        configuration.validate();
        this.configuration = configuration;
        this.menuConfiguration = new DefaultMenuConfiguration();
        configuration.configure(menuConfiguration);
    }

    @Override
    protected void configure() {
        bind(ConfigurationHandler.class).toInstance(configuration);
        bind(MenuConfigurationHandler.class).toInstance(menuConfiguration);
        bind(JRPGConfiguration.class).toInstance(configuration);
        bind(GameLauncherFactory.class).to(ServiceLoaderGameLauncherFactory.class);
        bind(GdxFacade.class).to(DefaultGdxFacade.class);
        bind(GdxAIFacade.class).to(DefaultGdxAIFacade.class);
        bind(JRPGEngine.class).to(DefaultJRPGEngine.class);

        // content registries
        bind(CharacterAnimationSetRegistry.class).asEagerSingleton();
        bind(MapRegistry.class).asEagerSingleton();
        bind(ContentRegistry.class).to(DefaultContentRegistry.class);
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
