package com.github.jaystgelais.jrpg.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.jaystgelais.jrpg.Game;

public class GraphicsService {
    public static final int DEFAULT_RESOLUTION_WIDTH = 480;
    public static final int DEFAULT_RESOLUTION_HEIGHT = 320;
    private final AssetManager assetManager;

    public GraphicsService(final AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public final void runGame(final Game game) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "my-gdx-game";
        cfg.useGL30 = false;
        cfg.width = DEFAULT_RESOLUTION_WIDTH;
        cfg.height = DEFAULT_RESOLUTION_HEIGHT;

        new LwjglApplication(game, cfg);
    }
}
