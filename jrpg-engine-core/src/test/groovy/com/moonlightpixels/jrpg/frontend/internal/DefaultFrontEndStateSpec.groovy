package com.moonlightpixels.jrpg.frontend.internal

import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.ui.UserInterface
import spock.lang.Specification

class DefaultFrontEndStateSpec extends Specification {
    private UserInterface userInterface
    private DefaultFrontEndState frontEndState
    private JRPG jrpg

    void setup() {
        userInterface = Mock(UserInterface)
        frontEndState = new DefaultFrontEndState(userInterface)
        jrpg = Mock(JRPG)
    }

    void 'update() updates the UserInterface'() {
        when:
        frontEndState.update(jrpg)

        then:
        1 * userInterface.update()
    }
}
