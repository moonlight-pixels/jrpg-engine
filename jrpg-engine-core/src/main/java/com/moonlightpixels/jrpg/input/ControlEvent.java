package com.moonlightpixels.jrpg.input;

/**
 * Events UIs and Actors can listen for from Keyboard and Controller schemes.
 */
public enum ControlEvent {
    /**
     * Up key/axis pressed.
     */
    UP_PRESSED,
    /**
     * Up key/axis released.
     */
    UP_RELEASED,
    /**
     * Down key/axis pressed.
     */
    DOWN_PRESSED,
    /**
     * Down key/axis released.
     */
    DOWN_RELEASED,
    /**
     * Left key/axis pressed.
     */
    LEFT_PRESSED,
    /**
     * Left key/axis released.
     */
    LEFT_RELEASED,
    /**
     * Right key/axis pressed.
     */
    RIGHT_PRESSED,
    /**
     * Right key/axis released.
     */
    RIGHT_RELEASED,
    /**
     * Action key/button pressed.
     */
    ACTION_PRESSED,
    /**
     * Cancel key/button pressed.
     */
    CANCEL_PRESSED,
    /**
     * Menu key/button pressed.
     */
    MENU_PRESSED
}
