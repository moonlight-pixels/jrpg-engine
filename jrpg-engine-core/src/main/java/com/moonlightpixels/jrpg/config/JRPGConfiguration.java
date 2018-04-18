package com.moonlightpixels.jrpg.config;

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
}
