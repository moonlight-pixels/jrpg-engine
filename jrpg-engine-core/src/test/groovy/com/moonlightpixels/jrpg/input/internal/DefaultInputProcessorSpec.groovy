package com.moonlightpixels.jrpg.input.internal

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.moonlightpixels.jrpg.input.ClickEvent
import com.moonlightpixels.jrpg.input.ControlEvent
import com.moonlightpixels.jrpg.input.KeyboardMapping
import spock.lang.Specification

class DefaultInputProcessorSpec extends Specification {
    private ControlEventGateway controllerEventGateway
    private ClickEventGateway clickEventGateway
    private KeyboardMapping keyboardMapping
    private InputProcessor inputProcessor

    void setup() {
        controllerEventGateway = Mock(ControlEventGateway)
        clickEventGateway = Mock(ClickEventGateway)
        keyboardMapping = new KeyboardMapping()
            .registerUpKey(Input.Keys.UP)
            .registerDownKey(Input.Keys.DOWN)
            .registerLeftKey(Input.Keys.LEFT)
            .registerRightKey(Input.Keys.RIGHT)
            .registerActionKey(Input.Keys.ENTER)
            .registerCancelKey(Input.Keys.TAB)
            .registerMenuKey(Input.Keys.SPACE)
        inputProcessor = new DefaultInputProcessor(controllerEventGateway, clickEventGateway, keyboardMapping)
    }

    void 'keyDown fires correct event if key is mapped'() {
        when:
        inputProcessor.keyDown(Input.Keys.UP)

        then:
        1 * controllerEventGateway.fireEvent(ControlEvent.UP_PRESSED)
    }

    void 'keyDown does not fire an event if no key is mapped'() {
        when:
        inputProcessor.keyDown(Input.Keys.A)

        then:
        0 * controllerEventGateway.fireEvent(_)
    }

    void 'keyUp fires correct event if key is mapped'() {
        when:
        inputProcessor.keyUp(Input.Keys.UP)

        then:
        1 * controllerEventGateway.fireEvent(ControlEvent.UP_RELEASED)
    }

    void 'keyUp does not fire an event if no key is mapped'() {
        when:
        inputProcessor.keyUp(Input.Keys.SPACE)

        then:
        0 * controllerEventGateway.fireEvent(_)
    }

    void 'touchDown fires ClickEvent'() {
        when:
        inputProcessor.touchDown(1, 1, 1, 1, )

        then:
        1 * clickEventGateway.fireEvent(new ClickEvent(1, 1))
    }
}
