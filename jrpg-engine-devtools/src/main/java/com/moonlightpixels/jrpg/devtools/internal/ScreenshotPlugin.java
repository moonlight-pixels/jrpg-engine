package com.moonlightpixels.jrpg.devtools.internal;

import com.badlogic.gdx.Input;
import com.moonlightpixels.jrpg.devtools.internal.input.KeyWatcher;
import com.moonlightpixels.jrpg.internal.plugin.Plugin;
import com.moonlightpixels.jrpg.internal.plugin.PluginHandlerRegistry;

public final class ScreenshotPlugin implements Plugin {
    private final KeyWatcher screenshotKey;
    private final ScreenshotTaker screenshotTaker;

    public ScreenshotPlugin() {
        this(
            new KeyWatcher(Input.Keys.NUM_1),
            new DefaultScreenshotTaker()
        );
    }

    ScreenshotPlugin(final KeyWatcher screenshotKey, final ScreenshotTaker screenshotTaker) {
        this.screenshotKey = screenshotKey;
        this.screenshotTaker = screenshotTaker;
    }

    @Override
    public void init(final PluginHandlerRegistry handlerRegistry) {
        handlerRegistry.registerPostRenderAction(() -> {
            if (screenshotKey.hasBeenPressed()) {
                screenshotTaker.take();
            }
        });
    }
}
