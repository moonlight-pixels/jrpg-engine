package com.moonlightpixels.jrpg.tween;

import lombok.Builder;

public final class IntegerTween extends AbstractTween<Integer> {

    @Builder
    private IntegerTween(final TweenFunction tweenFunction, final Integer start, final Integer end,
                        final float totalTweenTime, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTime, isRepeating);
    }

    @Override
    protected Integer getValue(final float percentComplete, final Integer start, final Integer end) {
        return start + Math.round(percentComplete * (end - start));
    }

    public static class IntegerTweenBuilder {
        private TweenFunction tweenFunction = new ConstantVelocityTweenFunction();
        private boolean isRepeating = false;
    }
}
