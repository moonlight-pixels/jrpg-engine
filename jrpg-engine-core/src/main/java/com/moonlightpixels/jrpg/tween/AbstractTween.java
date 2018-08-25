package com.moonlightpixels.jrpg.tween;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractTween<T> implements Tween<T> {
    private final TweenFunction tweenFunction;
    private final T start;
    private final T end;
    private final float totalTweenTime;
    private final boolean isRepeating;
    private float timeInTween = 0f;

    @Override
    public final void update(final float elapsedTime) {
        timeInTween += elapsedTime;
        if (isRepeating) {
            timeInTween = timeInTween % totalTweenTime;
        }
    }

    @Override
    public final T getValue() {
        return getValue(tweenFunction.getPercentBetweenPoints(getPercentTimeElapsed()), start, end);
    }

    @Override
    public final boolean isComplete() {
        return !isRepeating && timeInTween >= totalTweenTime;
    }

    protected abstract T getValue(float percentComplete, T start, T end);

    private float getPercentTimeElapsed() {
        return Math.min((float) timeInTween / totalTweenTime, 1.0f);
    }
}
