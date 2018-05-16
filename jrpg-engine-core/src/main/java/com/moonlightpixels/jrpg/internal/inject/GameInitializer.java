package com.moonlightpixels.jrpg.internal.inject;

import com.moonlightpixels.jrpg.config.JRPGConfiguration;

public interface GameInitializer {
    void initialize(JRPGConfiguration configuration);
}
