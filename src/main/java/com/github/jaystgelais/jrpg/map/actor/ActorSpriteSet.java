package com.github.jaystgelais.jrpg.map.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.map.animation.SpriteSet;

import java.util.HashMap;
import java.util.Map;

public final class ActorSpriteSet extends SpriteSet {
    private final long timeToTraverseTileMs;
    private final Map<Direction, Animation<TextureRegion>> walking = new HashMap<>();
    private final Map<Direction, TextureRegion> standing = new HashMap<>();

    public ActorSpriteSet(final long timeToTraverseTileMs, final int spriteHeight, final int spriteWidth) {
        super(spriteHeight, spriteWidth);
        this.timeToTraverseTileMs = timeToTraverseTileMs;
    }

    public long getTimeToTraverseTileMs() {
        return timeToTraverseTileMs;
    }

    public Animation<TextureRegion> getWalkingAnimation(final Direction direction) {
        return walking.get(direction);
    }

    public void setWalkinAnimation(final Direction direction, final TextureRegion[] frames) {
        walking.put(direction, buildAnimation(frames, timeToTraverseTileMs));
    }

    public TextureRegion getStandingImage(final Direction direction) {
        return standing.get(direction);
    }

    public void setStandingImage(final Direction direction, final TextureRegion textureRegion) {
        standing.put(direction, textureRegion);
    }

}
