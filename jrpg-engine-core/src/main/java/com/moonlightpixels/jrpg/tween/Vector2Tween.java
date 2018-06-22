package com.moonlightpixels.jrpg.tween;

import com.badlogic.gdx.math.Vector2;

public final class Vector2Tween extends AbstractTween<Vector2> {

    /**
     * Construct a Vector2Tween.
     *
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     */
    public Vector2Tween(final Vector2 start, final Vector2 end, final float totalTweenTime) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTime);
    }

    /**
     * Construct a Vector2Tween.
     *
     * @param tweenFunction tween function
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     */
    public Vector2Tween(final TweenFunction tweenFunction, final Vector2 start, final Vector2 end,
                        final float totalTweenTime) {
        super(tweenFunction, start, end, totalTweenTime);
    }

    /**
     * Construct a Vector2Tween.
     *
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     * @param isRepeating whether or not the tween repeats/loops
     */
    public Vector2Tween(final Vector2 start, final Vector2 end, final float totalTweenTime, final boolean isRepeating) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTime, isRepeating);
    }

    /**
     * Construct a Vector2Tween.
     *
     * @param tweenFunction tween function
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     * @param isRepeating whether or not the tween repeats/loops
     */
    public Vector2Tween(final TweenFunction tweenFunction, final Vector2 start, final Vector2 end,
                        final float totalTweenTime, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTime, isRepeating);
    }

    @Override
    protected Vector2 getValue(final float percentComplete, final Vector2 start, final Vector2 end) {
        return new Vector2(
            start.x + (percentComplete * (end.x - start.x)),
            start.y + (percentComplete * (end.y - start.y))
        );
    }
}
