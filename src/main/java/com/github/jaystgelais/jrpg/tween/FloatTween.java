package com.github.jaystgelais.jrpg.tween;

public final class FloatTween extends AbstractTween<Float> {

    public FloatTween(final Float start, final Float end, final long totalTweenTimeMs) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTimeMs);
    }

    public FloatTween(final TweenFunction tweenFunction, final Float start, final Float end,
                      final long totalTweenTimeMs, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTimeMs, isRepeating);
    }

    public FloatTween(final Float start, final Float end, final long totalTweenTimeMs,
                        final boolean isRepeating) {
        this(new ConstantVelocityTweenFunction(), start, end, totalTweenTimeMs, isRepeating);
    }

    public FloatTween(final TweenFunction tweenFunction, final Float start, final Float end,
                      final long totalTweenTimeMs) {
        super(tweenFunction, start, end, totalTweenTimeMs);
    }

    @Override
    protected Float getValue(final float percentComplete, final Float start, final Float end) {
        return start + (percentComplete * (end - start));
    }
}
