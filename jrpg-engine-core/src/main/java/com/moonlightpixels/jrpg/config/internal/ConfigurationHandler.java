package com.moonlightpixels.jrpg.config.internal;

import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import com.moonlightpixels.jrpg.ui.UiStyle;

public interface ConfigurationHandler {
    void validate() throws IllegalStateException;
    void configure(UiStyle uiStyle);
    void configure(FrontEndConfig frontEnd);
}
