package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Manages scene2d stage containing user interface.
 */
public interface UserInterface {
    /**
     * Remove all objects from UserInterface.
     */
    void clear();

    /**
     * Add object to UserInterface.
     *
     * @param actor UI object to add.
     */
    void add(Actor actor);

    /**
     * Removes object from UserInterface.
     *
     * @param actor UI object to remove.
     */
    void remove(Actor actor);

    /**
     * Updates all UI Objects and renders stage.
     */
    void update();

    /**
     * Returns reference to the style store.
     *
     * @return UiStyle store
     */
    UiStyle getUiStyle();
}
