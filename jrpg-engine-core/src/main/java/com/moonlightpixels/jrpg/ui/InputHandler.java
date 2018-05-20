package com.moonlightpixels.jrpg.ui;

import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;

/**
 * Components that handle input need to be able to handle both ControlEvents and ClickEvents.
 */
public interface InputHandler {
    /**
     * Handle ControlEvent.
     *
     * @param event ControlEvent to handle
     * @return true if event was handled, false if event should be passed to next handler
     */
    boolean handleControlEvent(ControlEvent event);

    /**
     * Handle ClickEvent.
     *
     * @param event ClickEvent to handle
     * @return true if event was handled, false if event should be passed to next handler
     */
    boolean handleClickEvent(ClickEvent event);
}
