package com.moonlightpixels.jrpg.tween;

/**
 * A tween function maps the total time elapsed for a Tween to a percentage of progress from a Tween's starting value
 * to it's end value.
 */
public interface TweenFunction {
    /**
     * Returns the percentage of progress from a Tween's starting value to it's end value for the given elapsed time.
     *
     * @param percentTimeElapsed Percentage (0.0 - 1.0) of time elapsed
     * @return Percentage (0.0 - 1.0) between a Tween's starting value and it's end value
     */
    float getPercentBetweenPoints(float percentTimeElapsed);
}
