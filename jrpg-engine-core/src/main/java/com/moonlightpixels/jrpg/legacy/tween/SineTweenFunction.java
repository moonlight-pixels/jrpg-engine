package com.moonlightpixels.jrpg.legacy.tween;

public final class SineTweenFunction implements TweenFunction {
    private static final double PI2 = (Math.PI * 2.0f);

    @Override
    public float getPercentBetweenPoints(final float percentTimeElapsed) {
        return ((float) Math.sin(percentTimeElapsed * PI2) + 1.0f) / 2.0f;
    }
}
