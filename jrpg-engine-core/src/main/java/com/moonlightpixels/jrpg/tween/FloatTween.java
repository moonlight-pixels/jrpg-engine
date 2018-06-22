package com.moonlightpixels.jrpg.tween;

public final class FloatTween extends AbstractTween<Float> {

    /**
     * Construct a FloatTween.
     *
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     */
    public FloatTween(final Float start, final Float end, final float totalTweenTime) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTime);
    }

    /**
     * Construct a FloatTween.
     *
     * @param tweenFunction tween function
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     * @param isRepeating whether or not the tween repeats/loops
     */
    public FloatTween(final TweenFunction tweenFunction, final Float start, final Float end,
                      final float totalTweenTime, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTime, isRepeating);
    }

    /**
     * Construct a FloatTween.
     *
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     * @param isRepeating whether or not the tween repeats/loops
     */
    public FloatTween(final Float start, final Float end, final float totalTweenTime,
                        final boolean isRepeating) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTime, isRepeating);
    }

    /**
     * Construct a FloatTween.
     *
     * @param tweenFunction tween function
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     */
    public FloatTween(final TweenFunction tweenFunction, final Float start, final Float end,
                      final float totalTweenTime) {
        super(tweenFunction, start, end, totalTweenTime);
    }

    @Override
    protected Float getValue(final float percentComplete, final Float start, final Float end) {
        return start + (percentComplete * (end - start));
    }
}
