package com.github.jaystgelais.jrpg;

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.github.jaystgelais.jrpg.inject.JRPGModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public final class GameRunner {
    static {
        GdxNativesLoader.load();
    }

    private final Game game;

    public GameRunner(final JRPGModule jrpgModule) {
        final Injector injector = Guice.createInjector(jrpgModule);
        game = injector.getInstance(Game.class);
    }

    public void start() {
        start(false);
    }

    public void start(final boolean debug) {
        game.setDebug(debug);
        game.start();
    }
}
