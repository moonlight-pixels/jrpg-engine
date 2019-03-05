package com.moonlightpixels.jrpg.devtools.internal.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import spock.lang.Specification

class KeyWatcherSpec extends Specification {
    private static final int TEST_KEY = Input.Keys.SPACE

    private GdxFacade gdx
    private KeyWatcher keyWatcher

    void setup() {
        gdx = Mock()
        keyWatcher = new KeyWatcher(gdx, TEST_KEY)
    }

    void 'hasBeenPressed only returns true once for each press/release of key'() {
        setup:
        boolean currentKeyState = false
        gdx.getInput() >> Mock(Input) {
            isKeyPressed(TEST_KEY) >> {
                currentKeyState
            }
        }

        expect:
        !keyWatcher.hasBeenPressed()

        when:
        currentKeyState = true

        then:
        !keyWatcher.hasBeenPressed()

        when:
        currentKeyState = false

        then:
        keyWatcher.hasBeenPressed()

        then:
        !keyWatcher.hasBeenPressed()
    }

    void 'public constructor uses real Gdx classes'() {
        setup:
        Input realInput = Gdx.input
        Input mockInput = Mock()
        Gdx.input = mockInput

        when:
        new KeyWatcher(TEST_KEY).hasBeenPressed()

        then:
        1 * mockInput.isKeyPressed(TEST_KEY)

        cleanup:
        Gdx.input = realInput
    }
}
