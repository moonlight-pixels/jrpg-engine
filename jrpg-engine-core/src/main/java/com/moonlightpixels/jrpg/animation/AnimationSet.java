package com.moonlightpixels.jrpg.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * An animation set provides access to animations in Map and Combat modes.
 *
 * @param <T> Type of animations in this set
 */
public interface AnimationSet<T extends AnimationSet.Type> {
    /**
     * Returns the height of frames in this AnimationSet.
     *
     * @return frame height
     */
    int getHeight();

    /**
     * Returns the width of frames in this AnimationSet.
     *
     * @return frame width
     */
    int getWidth();

    /**
     * Returns the animation for a given type with the provided duaration.
     *
     * @param type Type of animation
     * @param animationDuration Duration in seconds
     * @return the requested animation
     */
    Animation<TextureRegion> getAnimation(T type, float animationDuration);

    /**
     * Type specification for animations in a set.
     */
    interface Type {
        /**
         * Returns true if this animation type is required to be present in the set.
         *
         * @return true if this animation type is required to be present in the set
         */
        boolean isRequired();
    }
}
