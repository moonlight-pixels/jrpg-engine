package com.moonlightpixels.jrpg.internal.plugin;

public interface PluginHandlerRegistry {
    void registerPreRenderAction(PluginAction action);
    void registerPostRenderAction(PluginAction action);
}
