package com.github.jaystgelais.jrpg.demo;

import com.badlogic.gdx.assets.AssetManager;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.MapDefinition;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.ActorDefinition;
import com.github.jaystgelais.jrpg.map.actor.ActorSpriteSet;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetData;
import com.github.jaystgelais.jrpg.map.actor.WanderingNpcController;
import com.github.jaystgelais.jrpg.map.trigger.MessageTrigger;

public class CaveMapDefinition extends MapDefinition {
    public static final String MAP_PATH = "data/assets/maps/mapdemo/cave.tmx";
    public static final String NPC_SPRITE_PATH = "data/assets/sprites/mapdemo/npc.png";

    protected CaveMapDefinition(final GraphicsService graphicsService) {
        super(graphicsService);
    }

    @Override
    public GameMap getMap(final AssetManager assetManager) {
        GameMap map = loadMap(assetManager, MAP_PATH);
        map.addActor(new ActorDefinition() {
            @Override
            public Actor getActor(final GameMap map) {
                SpriteSetData spriteSetData = new SpriteSetData(NPC_SPRITE_PATH, 3, 4);
                ActorSpriteSet spriteSet = new ActorSpriteSet(
                        spriteSetData, 500, assetManager
                );

                return new Actor(map, spriteSet, new WanderingNpcController(), new TileCoordinate(10, 85));
            }
        }.getActor(map));

        map.addTrigger(new MessageTrigger(
                "Welcome to the JRPG demo. Press [GREEN]ENTER[] to dismiss this text. [GREEN]ARROW KEYS[] will move our hero around the cave. Press [GREEN]ESC[] to exit the demo.",
                getGraphicsService(), getGraphicsService().getResolutionWidth() / 2, getGraphicsService().getResolutionHeight() / 4) {
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

        return map;
    }
}
