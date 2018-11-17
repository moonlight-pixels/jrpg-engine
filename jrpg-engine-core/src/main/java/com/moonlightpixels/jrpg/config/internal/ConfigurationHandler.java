package com.moonlightpixels.jrpg.config.internal;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.standard.MenuConfiguration;

public interface ConfigurationHandler {
    void validate() throws IllegalStateException;
    void configure(UiStyle uiStyle);
    void configure(FrontEndConfig frontEnd);
    void configure(MenuConfiguration menuConfiguration);
    void configureNewGame(GameState gameState);
}
