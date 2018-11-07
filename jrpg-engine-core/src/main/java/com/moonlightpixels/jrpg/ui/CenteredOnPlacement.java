package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Data;

@Data
public final class CenteredOnPlacement implements ActorPlacement {
    private final float x;
    private final float y;

    @Override
    public float getX(final Actor actor) {
        return x - (actor.getWidth() / 2f);
    }

    @Override
    public float getY(final Actor actor) {
        return y - (actor.getHeight() / 2f);
    }
}
