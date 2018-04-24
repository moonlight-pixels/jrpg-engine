package com.moonlightpixels.jrpg.config;

import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.function.Consumer;

/**
 * A buildable specification of a jrpg-engine game.
 */
public interface JRPGConfiguration {
    /**
     * Validates configuration.
     *
     * @throws IllegalStateException If Configuration is not valid.
     */
    void validate() throws IllegalStateException;

    /**
     * LaunchConfig for this game.
     *
     * @return LaunchConfig
     */
    LaunchConfig getLaunchConfig();

    /**
     * Sets the game's launchConfig.
     *
     * @param launchConfig launchConfig to use for this game
     * @return Refernce to this configuration
     */
    JRPGConfiguration setLaunchConfig(LaunchConfig launchConfig);

    /**
     * Configures UI Style.
     *
     * @param uiStyleConsumer Consumer that will configure the given Uistyle object.
     * @return Refernce to this configuration.
     */
    JRPGConfiguration uiStyle(Consumer<UiStyle> uiStyleConsumer);

    /**
     * Configures the uiStyle object passed.
     *
     * @param uiStyle UiStyle object to configure.
     */
    void configure(UiStyle uiStyle);
}
