package com.moonlightpixels.jrpg.internal.launch;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.ServiceLoader;

public final class ServiceLoaderGameLauncherFactory implements GameLauncherFactory {
    @Override
    public GameLauncher getGameLauncher() {
        ServiceLoader<GameLauncher> loader = ServiceLoader.load(GameLauncher.class);
        List<GameLauncher> launchers = Lists.newArrayList(loader);

        if (launchers.isEmpty()) {
            throw new IllegalStateException("No GameLauncher Found");
        }

        if (launchers.size() > 1) {
            throw new IllegalStateException("Multiple GameLaunchers found!");
        }

        return launchers.get(0);
    }
}
