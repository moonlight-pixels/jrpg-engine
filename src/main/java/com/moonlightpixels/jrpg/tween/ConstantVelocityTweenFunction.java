package com.moonlightpixels.jrpg.tween;

public final class ConstantVelocityTweenFunction implements TweenFunction {
    @Override
    public float getPercentBetweenPoints(final float percentTimeElapsed) {
        return percentTimeElapsed;
    }
}
