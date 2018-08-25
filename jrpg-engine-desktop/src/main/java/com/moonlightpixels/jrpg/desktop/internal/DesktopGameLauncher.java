package com.moonlightpixels.jrpg.desktop.internal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moonlightpixels.jrpg.config.LaunchConfig;
import com.moonlightpixels.jrpg.internal.launch.GameLauncher;

public final class DesktopGameLauncher implements GameLauncher {
    @Override
    public void launch(final ApplicationListener game, final LaunchConfig launchConfig) {
        new LwjglApplication(game, configure(launchConfig));
    }

    private LwjglApplicationConfiguration configure(final LaunchConfig launchConfig) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = launchConfig.getResolutionWidth();
        config.height = launchConfig.getResolutionHeight();
        config.useGL30 = false;
        config.forceExit = false;
        return config;
    }
}
