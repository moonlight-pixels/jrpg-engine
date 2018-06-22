package com.moonlightpixels.jrpg.tween;

public abstract class AbstractTween<T> implements Tween<T> {
    private final TweenFunction tweenFunction;
    private final T start;
    private final T end;
    private final float totalTweenTime;
    private final boolean isRepeating;
    private float timeInTween = 0f;

    AbstractTween(final TweenFunction tweenFunction, final T start, final T end, final float totalTweenTime,
                         final boolean isRepeating) {
        this.tweenFunction = tweenFunction;
        this.start = start;
        this.end = end;
        this.totalTweenTime = totalTweenTime;
        this.isRepeating = isRepeating;
    }

    AbstractTween(final TweenFunction tweenFunction, final T start, final T end, final float totalTweenTime) {
        this(tweenFunction, start, end, totalTweenTime, false);
    }

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
