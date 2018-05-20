package com.moonlightpixels.jrpg.input;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Data;

@Data
public final class ClickEvent {
    private final int screenX;
    private final int screenY;

    /**
     * Returns true if this ClickEvent points to a point inside the actor.
     *
     * @param actor Actor to test
     * @return true if this ClickEvent points to a point inside the actor, false otherwise
     */
    public boolean appliesTo(final Actor actor) {
        return actor.getX() < screenX
            && (actor.getX() + actor.getWidth()) > screenX
            && actor.getY() < screenY
            && (actor.getY() + actor.getHeight()) > screenY;
    }
}
