package com.moonlightpixels.jrpg.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moonlightpixels.jrpg.util.TimeUtil;

public class SpriteSet {
    private final int spriteHeight;
    private final int spriteWidth;

    public SpriteSet(final int spriteHeight, final int spriteWidth) {
        this.spriteHeight = spriteHeight;
        this.spriteWidth = spriteWidth;
    }

    public final int getSpriteHeight() {
        return spriteHeight;
    }

    public final int getSpriteWidth() {
        return spriteWidth;
    }

    protected final Animation<TextureRegion> buildAnimation(final TextureRegion[] frames, final long totalTimeMs) {
        return new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(totalTimeMs) / frames.length,
                frames
        );
    }
}
