package com.moonlightpixels.jrpg.legacy.tween;

public final class IntegerTween extends AbstractTween<Integer> {

    public IntegerTween(final Integer start, final Integer end, final long totalTweenTimeMs) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTimeMs);
    }

    public IntegerTween(final TweenFunction tweenFunction, final Integer start,
                        final Integer end, final long totalTweenTimeMs) {
        super(tweenFunction, start, end, totalTweenTimeMs);
    }

    public IntegerTween(final Integer start, final Integer end, final long totalTweenTimeMs,
                        final boolean isRepeating) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTimeMs, isRepeating);
    }

    public IntegerTween(final TweenFunction tweenFunction, final Integer start, final Integer end,
                        final long totalTweenTimeMs, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTimeMs, isRepeating);
    }

    @Override
    protected Integer getValue(final float percentComplete, final Integer start, final Integer end) {
        return start + Math.round(percentComplete * (end - start));
    }
}
