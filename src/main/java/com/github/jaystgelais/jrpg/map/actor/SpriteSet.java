package com.github.jaystgelais.jrpg.map.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public final class SpriteSet {
    private final long timeToTraverseTileMs;
    private final Map<Direction, Animation<TextureRegion>> walking = new HashMap<>();
    private final Map<Direction, TextureRegion> standing = new HashMap<>();

    public SpriteSet(final long timeToTraverseTileMs) {
        this.timeToTraverseTileMs = timeToTraverseTileMs;
    }

    public long getTimeToTraverseTileMs() {
        return timeToTraverseTileMs;
    }

    public Animation<TextureRegion> getWalkingAnimation(final Direction direction) {
        return walking.get(direction);
    }

    public void setWalkinAnimation(final Direction direction, final TextureRegion[] frames) {
        walking.put(direction, buildAnimation(frames));
    }

    public TextureRegion getStandingImage(final Direction direction) {
        return standing.get(direction);
    }

    public void setStandingImage(final Direction direction, final TextureRegion textureRegion) {
        standing.put(direction, textureRegion);
    }

    private Animation<TextureRegion> buildAnimation(final TextureRegion[] frames) {
        return new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(timeToTraverseTileMs) / frames.length,
                frames
        );
    }

}
