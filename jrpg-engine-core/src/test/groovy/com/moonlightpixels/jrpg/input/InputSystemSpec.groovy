package com.moonlightpixels.jrpg.input

import com.moonlightpixels.jrpg.ui.InputHandler
import spock.lang.Specification

class InputSystemSpec extends Specification {
    private InputSystem inputSystem
    private InputScheme activeInputScheme
    private InputHandler inputHandler
    private ClickEvent mockClickEvent

    void setup() {
        mockClickEvent = new ClickEvent(1, 1)
        inputSystem = new InputSystem() {
            @Override
            InputScheme getInputScheme() {
                return activeInputScheme
            }

            @Override
            void useKeyboard(final KeyboardMapping keyboardMapping) { }

            @Override
            Optional<ControlEvent> readControlEvent() {
                return Optional.of(ControlEvent.ACTION_PRESSED)
            }

            @Override
            Optional<ClickEvent> readClickEvent() {
                return Optional.of(mockClickEvent)
            }
        }
        inputHandler = Mock()
    }

    void 'click events are passed along if input scheme is TouchMouse'() {
        setup:
        activeInputScheme = InputScheme.TouchMouse

        when:
        inputSystem.passEventsToHandler(inputHandler)

        then:
        1 * inputHandler.handleClickEvent(mockClickEvent)
        0 * inputHandler.handleControlEvent(_)
    }

    void 'control events are passed along if input scheme is Keyboard'() {
        setup:
        activeInputScheme = InputScheme.Keyboard

        when:
        inputSystem.passEventsToHandler(inputHandler)

        then:
        1 * inputHandler.handleControlEvent(ControlEvent.ACTION_PRESSED)
        0 * inputHandler.handleClickEvent(_)
    }

    void 'control events are passed along if input scheme is Controller'() {
        setup:
        activeInputScheme = InputScheme.Controller

        when:
        inputSystem.passEventsToHandler(inputHandler)

        then:
        1 * inputHandler.handleControlEvent(ControlEvent.ACTION_PRESSED)
        0 * inputHandler.handleClickEvent(_)
    }
}
