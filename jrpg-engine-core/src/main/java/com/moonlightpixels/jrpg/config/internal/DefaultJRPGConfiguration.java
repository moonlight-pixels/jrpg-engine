package com.moonlightpixels.jrpg.config.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.LaunchConfig;
import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public final class DefaultJRPGConfiguration implements JRPGConfiguration {
    private LaunchConfig launchConfig;
    private final List<Consumer<UiStyle>> uiStyleConsumers = new LinkedList<>();

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
    public void configure(final UiStyle uiStyle) {
        uiStyleConsumers.forEach(consumer -> consumer.accept(uiStyle));
    }
}
