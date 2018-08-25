package com.moonlightpixels.jrpg.tween;

import lombok.Builder;

public final class FloatTween extends AbstractTween<Float> {

    @Builder
    private FloatTween(final TweenFunction tweenFunction, final Float start, final Float end,
                      final float totalTweenTime, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTime, isRepeating);
    }

    @Override
    protected Float getValue(final float percentComplete, final Float start, final Float end) {
        return start + (percentComplete * (end - start));
    }

    public static class FloatTweenBuilder {
        private TweenFunction tweenFunction = new ConstantVelocityTweenFunction();
        private boolean isRepeating = false;
    }
}
