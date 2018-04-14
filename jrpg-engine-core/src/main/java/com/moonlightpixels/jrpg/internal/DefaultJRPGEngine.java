package com.moonlightpixels.jrpg.internal;

import com.moonlightpixels.jrpg.JRPGEngine;
import com.moonlightpixels.jrpg.JRPGSpec;

import java.util.function.Consumer;

public final class DefaultJRPGEngine implements JRPGEngine {
    private final Consumer<? super JRPGSpec> definition;

    public DefaultJRPGEngine(final Consumer<? super JRPGSpec> definition) {
        this.definition = definition;
    }

    @Override
    public void run() {
        definition.accept(null);
    }
}
