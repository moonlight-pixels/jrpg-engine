package com.moonlightpixels.jrpg.input.internal

import com.badlogic.gdx.Application
import com.badlogic.gdx.Audio
import com.badlogic.gdx.Files
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.Net
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.moonlightpixels.jrpg.input.ClickEvent
import com.moonlightpixels.jrpg.input.ControlEvent
import com.moonlightpixels.jrpg.input.InputScheme
import com.moonlightpixels.jrpg.input.KeyboardMapping
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import spock.lang.Specification

class DefaultInputSystemSpec extends Specification {
    private GdxFacade gdx
    private Input mockInput
    private DefaultInputSystem inputSystem

    void setup() {
        gdx = mockGdx()
        inputSystem = new DefaultInputSystem(gdx)
    }

    void 'useKeyboard registers a Keyboard handler'() {
        when:
        inputSystem.useKeyboard(KeyboardMapping.DEFAULT)

        then:
        1 * mockInput.setInputProcessor(new DefaultInputProcessor(inputSystem, inputSystem, KeyboardMapping.DEFAULT))
        inputSystem.inputScheme == InputScheme.Keyboard
    }

    void 'fireEvent(keyboard) saves last event fired when Keyboard scheme is active'() {
        given:
        inputSystem.useKeyboard(KeyboardMapping.DEFAULT)

        when:
        inputSystem.fireEvent(ControlEvent.ACTION_PRESSED)
        inputSystem.fireEvent(ControlEvent.CANCEL_PRESSED)

        then:
        inputSystem.readControlEvent().orElse(null) == ControlEvent.CANCEL_PRESSED
    }

    void 'fireEvent(keyboard) does nothing when Keyboard scheme is not active'() {
        when:
        inputSystem.fireEvent(ControlEvent.ACTION_PRESSED)

        then:
        inputSystem.readControlEvent().orElse(null) == null
    }

    void 'fireEvent(click) saves last event fired when TouchMouse scheme is active'() {
        given:
        inputSystem.useTouchMouse()

        when:
        inputSystem.fireEvent(new ClickEvent(1, 1))
        inputSystem.fireEvent(new ClickEvent(2, 2))

        then:
        inputSystem.readClickEvent().orElse(null) == new ClickEvent(2, 2)
    }

    void 'fireEvent(click) does nothing when TouchMouse scheme is not active'() {
        when:
        inputSystem.fireEvent(new ClickEvent(1, 1))

        then:
        inputSystem.readClickEvent().orElse(null) == null
    }

    private GdxFacade mockGdx() {
        mockInput = Mock(Input)
        return new GdxFacade() {
            @Override
            Application getApp() {
                return null
            }

            @Override
            Graphics getGraphics() {
                return null
            }

            @Override
            Audio getAudio() {
                return null
            }

            @Override
            Input getInput() {
                return mockInput
            }

            @Override
            Files getFiles() {
                return null
            }

            @Override
            Net getNet() {
                return null
            }

            @Override
            GL20 getGl() {
                return null
            }

            @Override
            GL20 getGl20() {
                return null
            }

            @Override
            GL30 getGl30() {
                return null
            }
        }
    }
}
