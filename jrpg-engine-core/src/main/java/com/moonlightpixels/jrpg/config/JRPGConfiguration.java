package com.moonlightpixels.jrpg.config;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.standard.MenuConfiguration;

import java.util.function.Consumer;

/**
 * A buildable specification of a jrpg-engine game.
 */
public interface JRPGConfiguration {
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
     * Configures game's front end mode.
     *
     * @param frontEndConsumer Consumer that will configure the given FrontEndConfig object.
     * @return Refernce to this configuration.
     */
    JRPGConfiguration frontEnd(Consumer<FrontEndConfig> frontEndConsumer);

    /**
     * Configures standard game menus.
     *
     * @param menuConsumer Consumer that will configure the given MenuConfiguration object.
     * @return Refernce to this configuration.
     */
    JRPGConfiguration menu(Consumer<MenuConfiguration> menuConsumer);

    /**
     * Configures "New Game" GameState.
     *
     * @param gameStateConsumer Consumer that will configure GameState for a new game
     * @return Refernce to this configuration.
     */
    JRPGConfiguration newGame(Consumer<GameState> gameStateConsumer);

    /**
     * Configureshe StatSystem.
     *
     * @param statSystemConsumer Consumer that will configure the StatSystem
     * @return Refernce to this configuration.
     */
    JRPGConfiguration stats(Consumer<StatSystem> statSystemConsumer);

    /**
     * Configures Content Registry.
     *
     * @param contentRegistryConsumer ContentRegistry
     * @return Refernce to this configuration.
     */
    JRPGConfiguration content(Consumer<ContentRegistry> contentRegistryConsumer);
}
