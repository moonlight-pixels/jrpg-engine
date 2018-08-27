package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.ai.fsm.State;

public interface MenuState extends State<Menu> {
    /**
     * Return the component input should be routed to.
     *
     * @return State's InputHandler.
     */
    InputHandler getInputHandler();

    /**
     * Can this menu be dismissed by a dismiss event.
     *
     * @return true if this menu can be dismissed, false otherwise
     */
    boolean isDismissible();
}
