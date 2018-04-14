package com.moonlightpixels.jrpg;

import com.moonlightpixels.jrpg.internal.DefaultJRPGEngine;

import java.util.function.Consumer;

/**
 * The entry point for creating and starting a jrpg-engine game.
 *
 * The {@link #of(Consumer)} static method is used to create a game.
 */
public interface JRPGEngine {

    /**
     * Creates a new, unstarted, JRPGEngine server from the given definition.
     *
     * The Consumer argument effectively serves as the definition of the game. It receives a mutable game builder style
     * object, a JRPGSpec.
     *
     * @param definition  Game Definition
     * @return A JRPGEngine
     */
    static JRPGEngine of(final Consumer<? super JRPGSpec> definition) {
        return new DefaultJRPGEngine(definition);
    }

    /**
     * Convenience method to define and start() the game in one go.
     *
     * @param definition Game Definition
     */
    static void run(final Consumer<? super JRPGSpec> definition) {
        of(definition).run();
    }

    /**
     * Starts the Game.
     */
    void run();
}
