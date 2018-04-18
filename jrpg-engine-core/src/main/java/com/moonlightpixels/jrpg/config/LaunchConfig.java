package com.moonlightpixels.jrpg.config;

import lombok.Builder;
import lombok.Data;

/**
 * Configuration of Game's screen resolution.
 */
@Data
@Builder
public class LaunchConfig {
    private final int resolutionWidth;
    private final int resolutionHeight;
    private final boolean fullscreen;
}
