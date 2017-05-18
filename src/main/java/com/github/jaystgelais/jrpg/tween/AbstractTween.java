package com.github.jaystgelais.jrpg.tween;

public abstract class AbstractTween<T> implements Tween<T> {
    private final TweenFunction tweenFunction;
    private final T start;
    private final T end;
    private final long totalTweenTimeMs;
    private long timeInTween = 0L;

    public AbstractTween(final TweenFunction tweenFunction, final T start, final T end, final long totalTweenTimeMs) {
        this.tweenFunction = tweenFunction;
        this.start = start;
        this.end = end;
        this.totalTweenTimeMs = totalTweenTimeMs;
    }

    @Override
    public final void update(final long elapsedTime) {
        timeInTween += elapsedTime;
    }

    @Override
    public final T getValue() {
        return getValue(tweenFunction.getPercentBetweenPoints(getPercentTimeElapsed()), start, end);
    }

    @Override
    public final boolean isComplete() {
        return timeInTween >= totalTweenTimeMs;
    }

    protected abstract T getValue(float percentComplete, T start, T end);

    private float getPercentTimeElapsed() {
        return Math.min((float) timeInTween / totalTweenTimeMs, 1.0f);
    }
}
