package com.moonlightpixels.jrpg.input

import com.badlogic.gdx.Input
import spock.lang.Specification

class KeyboardMappingSpec extends Specification {
    void 'Test Mapped Events'(int keyCode, ControlEvent pressedEvent,
                              ControlEvent releasedEvent) {
        expect:
        KeyboardMapping.DEFAULT.getPressedEvent(keyCode).orElse(null) == pressedEvent
        KeyboardMapping.DEFAULT.getReleasedEvent(keyCode).orElse(null) == releasedEvent

        where:
        keyCode          | pressedEvent                | releasedEvent
        Input.Keys.UP    | ControlEvent.UP_PRESSED     | ControlEvent.UP_RELEASED
        Input.Keys.DOWN  | ControlEvent.DOWN_PRESSED   | ControlEvent.DOWN_RELEASED
        Input.Keys.LEFT  | ControlEvent.LEFT_PRESSED   | ControlEvent.LEFT_RELEASED
        Input.Keys.RIGHT | ControlEvent.RIGHT_PRESSED  | ControlEvent.RIGHT_RELEASED
        Input.Keys.ENTER | ControlEvent.ACTION_PRESSED | null
        Input.Keys.TAB   | ControlEvent.CANCEL_PRESSED | null
        Input.Keys.SPACE | ControlEvent.MENU_PRESSED   | null
    }
}
