package com.moonlightpixels.jrpg.map.character;

import com.moonlightpixels.jrpg.animation.AnimationSet;
import com.moonlightpixels.jrpg.map.Direction;

import java.util.Arrays;

/**
 * Enumeration of required character animations.
 */
public enum CharacterAnimation implements AnimationSet.Type {
    /**
     * Walking up animation.
     */
    WalkUp(Direction.UP, CharacterAnimationAction.Walk),
    /**
     * Walking down animation.
     */
    WalkDown(Direction.DOWN, CharacterAnimationAction.Walk),
    /**
     * Walking right animation.
     */
    WalkRight(Direction.RIGHT, CharacterAnimationAction.Walk),
    /**
     * Walking left animation.
     */
    WalkLeft(Direction.LEFT, CharacterAnimationAction.Walk),
    /**
     * Standing facing up animation.
     */
    StandUp(Direction.UP, CharacterAnimationAction.Stand),
    /**
     * Standing facing down animation.
     */
    StandDown(Direction.DOWN, CharacterAnimationAction.Stand),
    /**
     * Standing facing right animation.
     */
    StandRight(Direction.RIGHT, CharacterAnimationAction.Stand),
    /**
     * Standing facing left animation.
     */
    StandLeft(Direction.LEFT, CharacterAnimationAction.Stand);

    private final Direction direction;
    private final CharacterAnimationAction animationAction;

    /**
     * Returns the walk animation for a given direction.
     *
     * @param direction Direction for walk animation
     * @return Animation
     */
    public static CharacterAnimation getWalkingAnimation(final Direction direction) {
        return Arrays.stream(values())
            .filter(characterAnimation -> (
                characterAnimation.animationAction == CharacterAnimationAction.Walk
                    && characterAnimation.direction == direction
            ))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the stainding animation for a given direction.
     *
     * @param direction Direction for standing animation
     * @return Animation
     */
    public static CharacterAnimation getStandingAnimation(final Direction direction) {
        return Arrays.stream(values())
            .filter(characterAnimation -> (
                characterAnimation.animationAction == CharacterAnimationAction.Stand
                    && characterAnimation.direction == direction
            ))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    CharacterAnimation(final Direction direction, final CharacterAnimationAction animationAction) {
        this.direction = direction;
        this.animationAction = animationAction;
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    private enum CharacterAnimationAction { Walk, Stand }
}
