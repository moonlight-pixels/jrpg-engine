package com.moonlightpixels.jrpg.internal.launch;

import com.badlogic.gdx.ApplicationListener;
import com.moonlightpixels.jrpg.config.LaunchConfig;

public interface GameLauncher {
    void launch(ApplicationListener game, LaunchConfig launchConfig);
}
