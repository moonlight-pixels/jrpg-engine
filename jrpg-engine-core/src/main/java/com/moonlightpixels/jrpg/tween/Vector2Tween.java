package com.moonlightpixels.jrpg.tween;

import com.badlogic.gdx.math.Vector2;
import lombok.Builder;

public final class Vector2Tween extends AbstractTween<Vector2> {

    @Builder
    private Vector2Tween(final TweenFunction tweenFunction, final Vector2 start, final Vector2 end,
                        final float totalTweenTime, final boolean isRepeating) {
        super(tweenFunction, start, end, totalTweenTime, isRepeating);
    }

    @Override
    protected Vector2 getValue(final float percentComplete, final Vector2 start, final Vector2 end) {
        return new Vector2(
            start.x + (percentComplete * (end.x - start.x)),
            start.y + (percentComplete * (end.y - start.y))
        );
    }

    public static class Vector2TweenBuilder {
        private TweenFunction tweenFunction = new ConstantVelocityTweenFunction();
        private boolean isRepeating = false;
    }
}
