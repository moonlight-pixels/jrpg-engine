package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Calculates position (x, y) of an actor.
 */
public interface ActorPlacement {
    /**
     * Returns caclulated X position for actor.
     *
     * @param actor Actor to position
     * @return X coordinate
     */
    float getX(Actor actor);

    /**
     * Returns caclulated Y position for actor.
     *
     * @param actor Actor to position
     * @return Y coordinate
     */
    float getY(Actor actor);
}
