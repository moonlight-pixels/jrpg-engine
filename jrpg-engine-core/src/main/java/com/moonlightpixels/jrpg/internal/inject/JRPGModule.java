package com.moonlightpixels.jrpg.internal.inject;

import com.google.inject.AbstractModule;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.DefaultGdxFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxAIFacade;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.launch.GameLauncherFactory;
import com.moonlightpixels.jrpg.internal.launch.ServiceLoaderGameLauncherFactory;

public final class JRPGModule extends AbstractModule {
    private final JRPGConfiguration configuration;

    public JRPGModule(final JRPGConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(JRPGConfiguration.class).toInstance(configuration);
        bind(GameLauncherFactory.class).to(ServiceLoaderGameLauncherFactory.class);
        bind(GdxFacade.class).to(DefaultGdxFacade.class);
        bind(GdxAIFacade.class).to(DefaultGdxAIFacade.class);
    }
}
