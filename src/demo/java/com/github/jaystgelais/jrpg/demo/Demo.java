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

//    private static Game initGame() {
//        return new Game(Collections.singleton(new PanelDemo()), "panelDemo", new GraphicsServiceImpl(initAssetManager()), new KeyboardInputService());
//    }

    private static Game initGame() {
        GraphicsService graphicsService = new GraphicsServiceImpl(initAssetManager());
        Game game = new Game(
                Collections.singleton(new MapMode(
                        graphicsService.getCamera(),
                        "data/assets/maps/mapdemo/cave.tmx",
                        new TileCoordinate(8, 97),
                        new SpriteSetData("data/assets/sprites/mapdemo/warrior.png", 3, 4))),
                "mapMode", graphicsService, new KeyboardInputService());
        game.setDebug(true);
        return game;
    }

    private Demo() { }
}
