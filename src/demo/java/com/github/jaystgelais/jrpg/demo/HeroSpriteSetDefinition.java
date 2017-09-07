package com.github.jaystgelais.jrpg.demo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.map.actor.Direction;
import com.github.jaystgelais.jrpg.map.actor.ActorSpriteSet;
import com.github.jaystgelais.jrpg.map.animation.SpriteSetDefinition;

public class HeroSpriteSetDefinition extends SpriteSetDefinition {
    private static final String SPRITE_SHEET_PATH = "data/assets/sprites/mapdemo/warrior.png";

    @Override
    public ActorSpriteSet getSpriteSet(final AssetManager assetManager) {
        TextureRegion[][] spriteSheet = loadSpriteSheet(SPRITE_SHEET_PATH, assetManager, 16, 18);
        ActorSpriteSet spriteSet = new ActorSpriteSet(300);

        spriteSet.setStandingImage(Direction.DOWN, spriteSheet[0][1]);
        spriteSet.setWalkinAnimation(Direction.DOWN, new TextureRegion[] {
                spriteSheet[0][0], spriteSheet[0][2]
        });

        spriteSet.setStandingImage(Direction.LEFT, spriteSheet[1][1]);
        spriteSet.setWalkinAnimation(Direction.LEFT, new TextureRegion[] {
                spriteSheet[1][0], spriteSheet[1][2]
        });

        spriteSet.setStandingImage(Direction.RIGHT, spriteSheet[2][1]);
        spriteSet.setWalkinAnimation(Direction.RIGHT, new TextureRegion[] {
                spriteSheet[2][0], spriteSheet[2][2]
        });

        spriteSet.setStandingImage(Direction.UP, spriteSheet[3][1]);
        spriteSet.setWalkinAnimation(Direction.UP, new TextureRegion[] {
                spriteSheet[3][0], spriteSheet[3][2]
        });

        return spriteSet;
    }
}
