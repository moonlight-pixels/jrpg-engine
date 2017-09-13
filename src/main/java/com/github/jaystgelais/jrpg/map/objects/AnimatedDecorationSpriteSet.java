package com.github.jaystgelais.jrpg.map.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.map.animation.SpriteSet;

public final class AnimatedDecorationSpriteSet extends SpriteSet {
    private final Animation<TextureRegion> animation;

    public AnimatedDecorationSpriteSet(final Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
