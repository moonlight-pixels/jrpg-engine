package com.github.jaystgelais.jrpg.demo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.GraphicsServiceImpl;
import com.github.jaystgelais.jrpg.input.KeyboardInputService;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetData;
import com.github.jaystgelais.jrpg.map.trigger.MessageTrigger;

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
        GraphicsService graphicsService = new GraphicsServiceImpl(initAssetManager());
        graphicsService.setResolutionWidth(480);
        graphicsService.setResolutionHeight(268);
        MapMode mapMode = new MapMode(
                graphicsService.getCamera(),
                "data/assets/maps/mapdemo/cave.tmx",
                new TileCoordinate(8, 97),
                new SpriteSetData("data/assets/sprites/mapdemo/warrior.png", 3, 4));
        mapMode.addTrigger(new MessageTrigger(
                "Welcome to the JRPG demo. Press [GREEN]ENTER[] to dismiss this text. [GREEN]ARROW KEYS[] will move our hero around the cave. Press [GREEN]ESC[] to exit the demo.",
                graphicsService, graphicsService.getResolutionWidth() / 2, graphicsService.getResolutionHeight() / 4) {
            boolean messageDisplayed = false;

            public boolean isTriggered(Actor hero) {
                if (messageDisplayed) {
                    return false;
                } else {
                    messageDisplayed = true;
                    return true;
                }
            }
        });
        Game game = new Game(
                Collections.singleton(mapMode),
                "mapMode", graphicsService, new KeyboardInputService());
        game.setDebug(true);
        return game;
    }

    private Demo() { }
}
