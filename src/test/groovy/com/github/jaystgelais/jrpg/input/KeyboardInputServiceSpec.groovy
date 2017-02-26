package com.github.jaystgelais.jrpg.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import spock.lang.Specification
import spock.lang.Unroll

class KeyboardInputServiceSpec extends Specification {
    @Unroll
    void 'Test default key mapping: #key maps to #input'() {
        setup:
        Input realInput = Gdx.input
        Gdx.input = Mock(Input) {
            1 * isKeyPressed(_) >> { arguments ->
                arguments[0] == key
            }
        }

        when:
        InputService inputService = new KeyboardInputService()

        then:
        inputService.checkForInput(input)

        cleanup:
        Gdx.input = realInput

        where:
        key          | input
        Keys.ENTER   | Inputs.OK
    }
}
