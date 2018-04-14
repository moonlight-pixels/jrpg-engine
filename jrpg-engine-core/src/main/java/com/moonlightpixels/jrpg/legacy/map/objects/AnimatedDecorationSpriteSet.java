package com.moonlightpixels.jrpg.legacy.map.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moonlightpixels.jrpg.legacy.animation.SpriteSet;

public final class AnimatedDecorationSpriteSet extends SpriteSet {
    private final Animation<TextureRegion> animation;

    public AnimatedDecorationSpriteSet(final Animation<TextureRegion> animation) {
        super(animation.getKeyFrames()[0].getRegionHeight(), animation.getKeyFrames()[0].getRegionWidth());
        this.animation = animation;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
