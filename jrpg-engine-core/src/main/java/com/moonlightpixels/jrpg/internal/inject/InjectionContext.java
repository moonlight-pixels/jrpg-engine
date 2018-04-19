package com.moonlightpixels.jrpg.internal.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public final class InjectionContext {
    private static Injector injector = Guice.createInjector();

    private InjectionContext() { }

    public static void addModule(final Module module) {
        injector = injector.createChildInjector(module);
    }

    public static Injector get() {
        return injector;
    }

    public static void reset() {
        injector = Guice.createInjector();
    }
}
