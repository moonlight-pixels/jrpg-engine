package com.github.jaystgelais.jrpg.demo;

import com.badlogic.gdx.assets.AssetManager;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.MapDefinition;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.*;
import com.github.jaystgelais.jrpg.map.trigger.*;

public class CaveMapDefinition extends MapDefinition {
    private static final String MAP_PATH = "data/assets/maps/mapdemo/cave.tmx";
    private static final String NPC_SPRITE_PATH = "data/assets/sprites/mapdemo/npc.png";

    @Override
    public GameMap getMap(final GraphicsService graphicsService, final AssetManager assetManager) {
        GameMap map = loadMap(graphicsService, assetManager, MAP_PATH);
        map.addActor(new SpriteSetDefinition() {
            @Override
            public Actor getActor(final GameMap map) {
                SpriteSetData spriteSetData = new SpriteSetData(NPC_SPRITE_PATH, 3, 4);
                SpriteSet spriteSet = new SpriteSet(
                        spriteSetData, 500, assetManager
                );

                return new Actor(map, spriteSet, new WanderingNpcController(), new TileCoordinate(10, 85));
            }
        }.getActor(map));

        map.addTrigger(new Trigger() {
            public boolean isTriggered(Actor hero) {
                if (GameState.checkFlag("demo.instructions.shown")) {
                    return false;
                } else {
                    GameState.setFlag("demo.instructions.shown", true);
                    return true;
                }
            }

            @Override
            public TriggerAction getAction() {
                return new MessageTriggerAction(
                        "Welcome to the JRPG demo. Press [GREEN]ENTER[] to dismiss this text. [GREEN]ARROW KEYS[] will move our hero around the cave. Press [GREEN]ESC[] to exit the demo.",
                        graphicsService
                );
            }
        });
        final MapDefinition thisMapDefinition = this;
        map.addTileTrigger(new TileCoordinate(79, 55), new TileTrigger() {
            @Override
            public TriggerAction onEnter() {
                return new WarpTriggerAction(thisMapDefinition, new TileCoordinate(8, 97), Direction.DOWN);
            }

            @Override
            public TriggerAction onExit() {
                return null;
            }

            @Override
            public TriggerAction onInspect() {
                return null;
            }
        });
        map.addTileTrigger(new TileCoordinate(8, 97), new TileTrigger() {
            @Override
            public TriggerAction onEnter() {
                return new WarpTriggerAction(thisMapDefinition, new TileCoordinate(79, 55), Direction.DOWN);
            }

            @Override
            public TriggerAction onExit() {
                return null;
            }

            @Override
            public TriggerAction onInspect() {
                return null;
            }
        });

        return map;
    }
}
