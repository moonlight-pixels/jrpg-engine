package com.github.jaystgelais.jrpg.map.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.util.TimeUtil;

public class SpriteSet {
    protected final Animation<TextureRegion> buildAnimation(final TextureRegion[] frames, final long totalTimeMs) {
        return new Animation<TextureRegion>(
                TimeUtil.convertMsToFloatSeconds(totalTimeMs) / frames.length,
                frames
        );
    }
}
