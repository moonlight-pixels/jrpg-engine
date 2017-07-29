package com.github.jaystgelais.jrpg.demo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.GraphicsServiceImpl;
import com.github.jaystgelais.jrpg.input.KeyboardInputService;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetData;

import java.util.Collections;

/**
 * Created by jgelais on 2/16/17.
 */
public final class Demo {

    public static void main(final String[] arg) {
        GdxNativesLoader.load();
        initGame().start();
    }

    private static AssetManager initAssetManager() {
        return new AssetManager();
    }

    private static Game initGame() {
        final AssetManager assetManager = initAssetManager();
        GraphicsService graphicsService = new GraphicsServiceImpl(assetManager);
        graphicsService.setResolutionWidth(480);
        graphicsService.setResolutionHeight(268);
        MapMode mapMode = new MapMode(
                new CaveMapDefinition(),
                new TileCoordinate(8, 97),
                new HeroSpriteSetDefinition());
        Game game = new Game(
                Collections.singleton(mapMode),
                "mapMode", graphicsService, new KeyboardInputService());
        game.setDebug(true);
        return game;
    }

    private Demo() { }
}
