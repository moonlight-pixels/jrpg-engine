package com.moonlightpixels.jrpg.internal.plugin;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public final class PluginSystem implements PluginHandlerRegistry {
    private final List<PluginAction> preRenderHandlers = new LinkedList<>();
    private final List<PluginAction> postRenderHandlers = new LinkedList<>();

    public void loadPlugins() {
        ServiceLoader.load(Plugin.class).forEach(plugin -> plugin.init(this));
    }

    public void preRender() {
        preRenderHandlers.forEach(PluginAction::perform);
    }

    public void postRender() {
        postRenderHandlers.forEach(PluginAction::perform);
    }

    @Override
    public void registerPreRenderAction(final PluginAction action) {
        preRenderHandlers.add(action);
    }

    @Override
    public void registerPostRenderAction(final PluginAction action) {
        postRenderHandlers.add(action);
    }
}
