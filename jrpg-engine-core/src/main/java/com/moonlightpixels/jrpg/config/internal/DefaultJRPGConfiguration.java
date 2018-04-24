package com.moonlightpixels.jrpg.config.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.LaunchConfig;
import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.Optional;
import java.util.function.Consumer;

public final class DefaultJRPGConfiguration implements JRPGConfiguration {
    private LaunchConfig launchConfig;
    private Consumer<UiStyle> uiStyleConsumer;

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
        this.uiStyleConsumer = uiStyleConsumer;
        return this;
    }

    @Override
    public void configure(final UiStyle uiStyle) {
        Optional.ofNullable(uiStyleConsumer).ifPresent(consumer -> consumer.accept(uiStyle));
    }
}
