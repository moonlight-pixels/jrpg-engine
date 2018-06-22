package com.moonlightpixels.jrpg.tween;

public final class IntegerTween extends AbstractTween<Integer> {

    /**
     * Construct an IntegerTween.
     *
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     */
    public IntegerTween(final Integer start, final Integer end, final float totalTweenTime) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTime);
    }

    /**
     * Construct an IntegerTween.
     *
     * @param tweenFunction tween function
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     */
    public IntegerTween(final TweenFunction tweenFunction, final Integer start,
                        final Integer end, final float totalTweenTime) {
        super(tweenFunction, start, end, totalTweenTime);
    }

    /**
     * Construct an IntegerTween.
     *
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     * @param isRepeating whether or not the tween repeats/loops
     */
    public IntegerTween(final Integer start, final Integer end, final float totalTweenTime,
                        final boolean isRepeating) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTime, isRepeating);
    }

    /**
     * Construct an IntegerTween.
     *
     * @param tweenFunction tween function
     * @param start staring value
     * @param end ending vale
     * @param totalTweenTime time (seconds) tween lasts
     * @param isRepeating whether or not the tween repeats/loops
     */
    public IntegerTween(final TweenFunction tweenFunction, final Integer start, final Integer end,
                        final float totalTweenTime, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTime, isRepeating);
    }

    @Override
    protected Integer getValue(final float percentComplete, final Integer start, final Integer end) {
        return start + Math.round(percentComplete * (end - start));
    }
}
