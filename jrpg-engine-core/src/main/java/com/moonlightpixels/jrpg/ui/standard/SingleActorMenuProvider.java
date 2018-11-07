package com.moonlightpixels.jrpg.ui.standard;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.ui.InputHandler;
import com.moonlightpixels.jrpg.ui.UiStyle;

/**
 * Provides a single (root) Actor to be added to the UI by a menu as well as the input handler for the menu to delgate
 * input events to.
 */
public interface SingleActorMenuProvider {
    /**
     * Root Actor for the menu.
     *
     * @param uiStyle UiStyle to be used when rendering the Actor.
     * @return Root Actor
     */
    Actor getActor(UiStyle uiStyle);

    /**
     * InputHandler to control menu actions.
     *
     * @return InputHandler
     */
    InputHandler getInputHandler();
}
