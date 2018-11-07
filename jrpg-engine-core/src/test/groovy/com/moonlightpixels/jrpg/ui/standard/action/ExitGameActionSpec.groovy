package com.moonlightpixels.jrpg.ui.standard.action

import com.badlogic.gdx.Application
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import spock.lang.Specification

class ExitGameActionSpec extends Specification {
    void 'Exits via GDX exit'() {
        setup:
        Application app = Mock()
        GdxFacade gdxFacade = Mock(GdxFacade) {
            getApp() >> app
        }
        ExitGameAction exitGameAction = new ExitGameAction(gdxFacade)

        when:
        exitGameAction.perform()

        then:
        1 * app.exit()
    }
}
