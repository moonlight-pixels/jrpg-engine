package com.moonlightpixels.jrpg.input;

/**
 * Input Schemes control how user input is translated into game action as well as informing the engine as to what
 * supporting UI elements are required.
 */
public enum InputScheme {
    /**
     * The TouchMouse scheme derives game actions from mouse clicks or touch screen taps.
     */
    TouchMouse,

    /**
     * The Keyboard scheme derives game actions from key presses.
     */
    Keyboard,

    /**
     * The Controller scheme derives game actions from axis and button presses.
     */
    Controller
}
