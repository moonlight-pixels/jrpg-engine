package com.moonlightpixels.jrpg.ui.standard;

import java.util.function.Consumer;

/**
 * Builder style configuration object providing access to the games standard menus.
 */
public interface MenuConfiguration {
    /**
     * Configures front end menu.
     *
     * @param menuConsumer Consumer that will configure the given FrontEndMenu object.
     * @return Refernce to this configuration.
     */
    MenuConfiguration frontend(Consumer<FrontEndMenu> menuConsumer);
}
