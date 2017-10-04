package com.github.jaystgelais.jrpg.map.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.map.animation.SpriteSet;

import java.util.HashMap;
import java.util.Map;

public final class ActorSpriteSet extends SpriteSet {
    private final int tilesPerCycle;
    private final Map<Direction, TextureRegion[]> walking = new HashMap<>();
    private final Map<Direction, TextureRegion> standing = new HashMap<>();

    public ActorSpriteSet(final int tilesPerCycle, final int spriteHeight, final int spriteWidth) {
        super(spriteHeight, spriteWidth);
        this.tilesPerCycle = tilesPerCycle;
    }

    public int getTilesPerCycle() {
        return tilesPerCycle;
    }

    public Animation<TextureRegion> getWalkingAnimation(final Direction direction, final long timeToTraverseTileMs) {
        return buildAnimation(walking.get(direction), timeToTraverseTileMs * tilesPerCycle);
    }

    public void setWalkinAnimation(final Direction direction, final TextureRegion[] frames) {
        walking.put(direction, frames);
    }

    public TextureRegion getStandingImage(final Direction direction) {
        return standing.get(direction);
    }

    public void setStandingImage(final Direction direction, final TextureRegion textureRegion) {
        standing.put(direction, textureRegion);
    }

}
