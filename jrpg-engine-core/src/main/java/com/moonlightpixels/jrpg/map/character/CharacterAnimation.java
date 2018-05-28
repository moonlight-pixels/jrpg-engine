package com.moonlightpixels.jrpg.map.character;

import com.moonlightpixels.jrpg.animation.AnimationSet;

/**
 * Enumeration of required character animations.
 */
public enum CharacterAnimation implements AnimationSet.Type {
    /**
     * Walking up animation.
     */
    WalkUp,
    /**
     * Walking down animation.
     */
    WalkDown,
    /**
     * Walking right animation.
     */
    WalkRight,
    /**
     * Walking left animation.
     */
    WalkLeft,
    /**
     * Standing facing up animation.
     */
    StandUp,
    /**
     * Standing facing down animation.
     */
    StandDown,
    /**
     * Standing facing right animation.
     */
    StandRight,
    /**
     * Standing facing left animation.
     */
    StandLeft;

    @Override
    public boolean isRequired() {
        return true;
    }
}
