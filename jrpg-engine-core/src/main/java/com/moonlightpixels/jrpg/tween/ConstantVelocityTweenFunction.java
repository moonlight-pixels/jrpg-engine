package com.moonlightpixels.jrpg.tween;

/**
 * Tween function where teh percent between points is equal to the percent of time passed.
 */
public final class ConstantVelocityTweenFunction implements TweenFunction {
    @Override
    public float getPercentBetweenPoints(final float percentTimeElapsed) {
        return percentTimeElapsed;
    }
}
