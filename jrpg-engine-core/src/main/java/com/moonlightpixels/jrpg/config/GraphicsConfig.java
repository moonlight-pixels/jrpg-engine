package com.moonlightpixels.jrpg.config;

import lombok.Builder;
import lombok.Data;

/**
 * Configuration of Game's screen resolution.
 */
@Data
@Builder
public class GraphicsConfig {
    private final float resolutionWidth;
    private final float resolutionHeight;
    private final boolean fullscreen;
}
