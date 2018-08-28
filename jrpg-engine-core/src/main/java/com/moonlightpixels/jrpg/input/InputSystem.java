package com.moonlightpixels.jrpg.input;

import com.moonlightpixels.jrpg.ui.InputHandler;

import java.util.Optional;

/**
 * An input system controlls how input peripherals control the game.
 */
public interface InputSystem {
    /**
     * Returns the active InputScheme.
     *
     * @return active InputScheme
     */
    InputScheme getInputScheme();

    /**
     * Sets the active InputScheme to use the hardware keyboard with the specified mapping.
     *
     * @param keyboardMapping Mapping of keys to game function
     */
    void useKeyboard(KeyboardMapping keyboardMapping);

    /**
     * Reads (ands removes for future reads) the last ControlEvent registered from active input device.
     *
     * @return Last read ControlEvent
     */
    Optional<ControlEvent> readControlEvent();

    /**
     * Reads (ands removes for future reads) the last ControlEvent registered from active input device.
     *
     * @return Last read ControlEvent
     */
    Optional<ClickEvent> readClickEvent();

    /**
     * Passes click/control events to input handler depending on curent input scheme.
     *
     * @param inputHandler Input handler to receive events
     */
    default void passEventsToHandler(final InputHandler inputHandler) {
        if (getInputScheme() == InputScheme.TouchMouse) {
            readClickEvent().ifPresent(inputHandler::handleClickEvent);
        } else {
            readControlEvent().ifPresent(inputHandler::handleControlEvent);
        }
    }
}
