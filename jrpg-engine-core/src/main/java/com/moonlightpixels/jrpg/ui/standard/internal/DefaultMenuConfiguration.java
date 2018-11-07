package com.moonlightpixels.jrpg.ui.standard.internal;

import com.moonlightpixels.jrpg.ui.standard.FrontEndMenu;
import com.moonlightpixels.jrpg.ui.standard.MenuConfiguration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public final class DefaultMenuConfiguration implements MenuConfiguration, MenuConfigurationHandler {
    private final List<Consumer<FrontEndMenu>> frontendMenuConsumers = new LinkedList<>();

    @Override
    public MenuConfiguration frontend(final Consumer<FrontEndMenu> frontendMenuConsumer) {
        frontendMenuConsumers.add(frontendMenuConsumer);
        return this;
    }

    @Override
    public void configureFrontEndMenu(final FrontEndMenu menu) {
        frontendMenuConsumers.forEach(consumer -> consumer.accept(menu));
    }
}
