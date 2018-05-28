package com.moonlightpixels.jrpg.map.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moonlightpixels.jrpg.animation.AnimationSet;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Set of animations for characters on a map.
 */
public final class CharacterAnimationSet implements AnimationSet<CharacterAnimation> {
    @Getter
    private final int height;
    @Getter
    private final int width;
    private final Map<CharacterAnimation, TextureRegion[]> animationframes = new HashMap<>();

    /**
     * Creates a CharacterAnimationSet with the given height and width.
     *
     * @param height frame height
     * @param width frame width
     */
    public CharacterAnimationSet(final int height, final int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Animation<TextureRegion> getAnimation(final CharacterAnimation type, final float animationDuration) {
        final TextureRegion[] frames = animationframes.get(type);
        return new Animation<>(animationDuration / frames.length, frames);
    }

    /**
     * Adds the animations frames for an animation type.
     *
     * @param type Animation type
     * @param frames Array of TextureRegions making up frames of an animation
     */
    public void addAnimationFrames(final CharacterAnimation type, final TextureRegion[] frames) {
        animationframes.put(type, frames);
    }

    /**
     * Throws IllegalStateException is any required animations are missing.
     */
    public void validate() {
        List<CharacterAnimation> missingAnimations =  Arrays.stream(CharacterAnimation.values())
            .filter(CharacterAnimation::isRequired)
            .filter(key -> !animationframes.containsKey(key))
            .collect(Collectors.toList());
        if (!missingAnimations.isEmpty()) {
            throw new IllegalStateException(String.format("Missing animations: %s.", missingAnimations));
        }
    }
}
