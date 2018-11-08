package com.moonlightpixels.jrpg.ui.standard

import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.scenes.scene2d.Actor
import com.moonlightpixels.jrpg.ui.Menu
import com.moonlightpixels.jrpg.ui.UiStyle
import com.moonlightpixels.jrpg.ui.UserInterface
import spock.lang.Specification

class SingleActorMenuStateSpec extends Specification {
    private SingleActorMenuState singleActorMenuState
    private SingleActorMenuProvider singleActorMenuProvider
    private Menu menu
    private UserInterface userInterface
    private UiStyle uiStyle

    void setup() {
        singleActorMenuProvider = Mock()
        singleActorMenuState = new SingleActorMenuState(singleActorMenuProvider, false)
        uiStyle = Mock()
        userInterface = Mock(UserInterface) {
            getUiStyle() >> uiStyle
        }
        menu = new Menu(userInterface, singleActorMenuState)
    }

    void 'enter() adds provided actor to UI'() {
        setup:
        Actor actor = Mock()
        singleActorMenuProvider.getActor(uiStyle) >> actor

        when:
        singleActorMenuState.enter(menu)

        then:
        1 * userInterface.add(actor)
    }

    void 'exit() removes provided actor to UI'() {
        setup:
        Actor actor = Mock()
        singleActorMenuProvider.getActor(uiStyle) >> actor
        singleActorMenuState.enter(menu)

        when:
        singleActorMenuState.exit(menu)

        then:
        1 * userInterface.remove(actor)
    }

    void 'update() does nothing'() {
        when:
        singleActorMenuState.update(menu)

        then:
        noExceptionThrown()
    }

    void 'onMessage() does nothing'() {
        when:
        singleActorMenuState.onMessage(menu, Mock(Telegram))

        then:
        noExceptionThrown()
    }
}
