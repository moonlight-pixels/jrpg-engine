package com.moonlightpixels.jrpg.config.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.config.ContentRegistry;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.LaunchConfig;
import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.standard.MenuConfiguration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public final class DefaultJRPGConfiguration implements JRPGConfiguration, ConfigurationHandler {
    private LaunchConfig launchConfig;
    private final List<Consumer<UiStyle>> uiStyleConsumers = new LinkedList<>();
    private final List<Consumer<FrontEndConfig>> frontEndConsumers = new LinkedList<>();
    private final List<Consumer<MenuConfiguration>> menuConsumers = new LinkedList<>();
    private final List<Consumer<GameState>> gameStateConsumers = new LinkedList<>();
    private final List<Consumer<StatSystem>> statSystemConsumers = new LinkedList<>();
    private final List<Consumer<ContentRegistry>> contentRegistryConsumers = new LinkedList<>();

    @Override
    public void validate() throws IllegalStateException {
        Preconditions.checkState(launchConfig != null, "Must provide a LaunchConfig.");
    }

    @Override
    public LaunchConfig getLaunchConfig() {
        return launchConfig;
    }

    @Override
    public JRPGConfiguration setLaunchConfig(final LaunchConfig launchConfig) {
        this.launchConfig = launchConfig;
        return this;
    }

    @Override
    public JRPGConfiguration uiStyle(final Consumer<UiStyle> uiStyleConsumer) {
        this.uiStyleConsumers.add(uiStyleConsumer);
        return this;
    }

    @Override
    public JRPGConfiguration frontEnd(final Consumer<FrontEndConfig> frontEndConsumer) {
        frontEndConsumers.add(frontEndConsumer);
        return this;
    }

    @Override
    public JRPGConfiguration menu(final Consumer<MenuConfiguration> menuConsumer) {
        menuConsumers.add(menuConsumer);
        return this;
    }

    @Override
    public JRPGConfiguration newGame(final Consumer<GameState> gameStateConsumer) {
        gameStateConsumers.add(gameStateConsumer);
        return this;
    }

    @Override
    public JRPGConfiguration stats(final Consumer<StatSystem> statSystemConsumer) {
        statSystemConsumers.add(statSystemConsumer);
        return this;
    }

    @Override
    public JRPGConfiguration content(final Consumer<ContentRegistry> contentRegistryConsumer) {
        contentRegistryConsumers.add(contentRegistryConsumer);
        return this;
    }

    @Override
    public void configure(final UiStyle uiStyle) {
        uiStyleConsumers.forEach(consumer -> consumer.accept(uiStyle));
    }

    @Override
    public void configure(final FrontEndConfig frontEnd) {
        frontEndConsumers.forEach(consumer -> consumer.accept(frontEnd));
    }

    @Override
    public void configure(final MenuConfiguration menuConfiguration) {
        menuConsumers.forEach(consumer -> consumer.accept(menuConfiguration));
    }

    @Override
    public void configureNewGame(final GameState gameState) {
        gameStateConsumers.forEach(consumer -> consumer.accept(gameState));
    }

    @Override
    public void configure(final StatSystem statSystem) {
        statSystemConsumers.forEach(consumer -> consumer.accept(statSystem));
    }

    @Override
    public void configureContent(final ContentRegistry contentRegistry) {
        contentRegistryConsumers.forEach(consumer -> consumer.accept(contentRegistry));
    }
}
