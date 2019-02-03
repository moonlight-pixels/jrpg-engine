package com.moonlightpixels.jrpg.combat.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.moonlightpixels.jrpg.animation.AnimationSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@SuppressWarnings("LineLength")
public final class CharacterBattleAnimationSet implements AnimationSet<CharacterBattleAnimationSet.CharacterBattleAnimation> {
    @Getter
    private final CharacterBattleAnimationSet.Key key;
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final int pixelsCoveredPerWalkCycle;
    private final Map<CharacterBattleAnimation, TextureRegion[]> animationframes = new HashMap<>();

    @Override
    public Animation<TextureRegion> getAnimation(final CharacterBattleAnimation type, final float animationDuration) {
        final TextureRegion[] frames = animationframes.get(type);
        return new Animation<>(animationDuration / frames.length, frames);
    }

    /**
     * Adds the animations frames for an animation type.
     *
     * @param type Animation type
     * @param frames Array of TextureRegions making up frames of an animation
     */
    public void addAnimationFrames(final CharacterBattleAnimation type, final TextureRegion[] frames) {
        animationframes.put(type, frames);
    }

    public interface CharacterBattleAnimation extends AnimationSet.Type { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public interface Key { }
}
