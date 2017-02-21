package com.github.jaystgelais.jrpg.input

import spock.lang.Specification

import java.time.Clock

/**
 * Created by jgelais on 2/20/17.
 */
class AbstractInputServiceSpec extends Specification {
    void 'isPressed honors inputDelayMs'() {
        setup:
        Clock mockClock = Mock(Clock) {
            3 * millis() >>> [1, 75, 150]
        }

        when:
        InputService inputService = new AbstractInputService(100, mockClock) {
            @Override
            protected boolean checkForInput(Inputs inputs) {
                return true
            }
        }

        then:
        inputService.isPressed(Inputs.OK)
        !inputService.isPressed(Inputs.OK)
        inputService.isPressed(Inputs.OK)
    }
}
