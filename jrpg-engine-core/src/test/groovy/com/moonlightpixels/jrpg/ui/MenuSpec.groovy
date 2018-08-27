package com.moonlightpixels.jrpg.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.moonlightpixels.jrpg.input.ClickEvent
import com.moonlightpixels.jrpg.input.ControlEvent
import com.moonlightpixels.jrpg.input.InputScheme
import spock.lang.Specification

import java.util.function.Consumer

class MenuSpec extends Specification {
    UserInterface userInterface

    void setup() {
        userInterface = Mock(UserInterface)
    }

    void 'opening the menu calls enter on initial state'() {
        setup:
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> Mock(InputHandler)
        }
        Menu menu = new Menu(userInterface, initialState)

        when:
        menu.open()

        then:
        1 * initialState.enter(menu)
    }

    void 'actors added to UI are removed on close'() {
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> Mock(InputHandler)
        }
        Menu menu = new Menu(userInterface, initialState)
        Actor actor1 = Mock (Actor)
        Actor actor2 = Mock (Actor)
        Actor actor3 = Mock (Actor)

        when:
        menu.open()
        menu.addActor(actor1)
        menu.addActor(actor2)
        menu.addActor(actor3)

        then:
        1 * userInterface.add(actor1)
        1 * userInterface.add(actor2)
        1 * userInterface.add(actor3)

        when:
        menu.removeActor(actor3)

        then:
        1 * userInterface.remove(actor3)

        when:
        menu.close()

        then:
        1 * userInterface.remove(actor1)
        1 * userInterface.remove(actor2)
        0 * userInterface.remove(actor3)
    }

    void 'control events are passed to active state'() {
        setup:
        InputHandler inputhandler = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler
        }
        Menu menu = new Menu(userInterface, initialState)

        when:
        menu.open()
        menu.handleControlEvent(ControlEvent.ACTION_PRESSED)

        then:
        1 * inputhandler.handleControlEvent(ControlEvent.ACTION_PRESSED)
    }

    void 'ControlEvent.CANCEL_PRESSED dismisses current state if dismissible'() {
        setup:
        InputHandler inputhandler = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler
        }
        MenuState newState = Mock(MenuState) {
            getInputHandler() >> inputhandler
            isDismissible() >> true
        }
        Menu menu = new Menu(userInterface, initialState)
        menu.setInputScheme(InputScheme.Keyboard)
        menu.open()
        menu.open(newState)

        when:
        menu.handleControlEvent(ControlEvent.CANCEL_PRESSED)

        then:
        1 * newState.exit(menu)
    }

    void 'ControlEvent.CANCEL_PRESSED does nothing if current state is not dismissible'() {
        setup:
        InputHandler inputhandler = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler
        }
        MenuState newState = Mock(MenuState) {
            getInputHandler() >> inputhandler
            isDismissible() >> false
        }
        Menu menu = new Menu(userInterface, initialState)
        menu.setInputScheme(InputScheme.Keyboard)
        menu.open()
        menu.open(newState)

        when:
        menu.handleControlEvent(ControlEvent.CANCEL_PRESSED)

        then:
        0 * newState.exit(menu)
    }

    void 'ControlEvent.CANCEL_PRESSED closes mene if no state to return to'() {
        setup:
        Consumer<Menu> onClose = Mock(Consumer)
        InputHandler inputhandler = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler
            isDismissible() >> true
        }
        Menu menu = new Menu(userInterface, initialState, onClose)
        menu.setInputScheme(InputScheme.Keyboard)
        menu.open()

        when:
        menu.handleControlEvent(ControlEvent.CANCEL_PRESSED)

        then:
        1 * onClose.accept(menu)
    }

    void 'click events are passed to active state'() {
        setup:
        InputHandler inputhandler = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler
        }
        Menu menu = new Menu(userInterface, initialState)
        ClickEvent event = new ClickEvent(1, 1)

        when:
        menu.open()
        menu.handleClickEvent(event)

        then:
        1 * inputhandler.handleClickEvent(event)
    }

    void 'Input scheme is passed down to active states and any opened states'() {
        setup:
        InputHandler inputhandler1 = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler1
        }
        InputHandler inputhandler2 = Mock(InputHandler)
        MenuState newState = Mock(MenuState) {
            getInputHandler() >> inputhandler2
        }
        Menu menu = new Menu(userInterface, initialState)

        when:
        menu.setInputScheme(InputScheme.Controller)
        menu.open()

        then:
        1 * inputhandler1.setInputScheme(InputScheme.Controller)

        when:
        menu.setInputScheme(InputScheme.Keyboard)

        then:
        1 * inputhandler1.setInputScheme(InputScheme.Keyboard)

        when:
        menu.open(newState)

        then:
        1 * inputhandler2.setInputScheme(InputScheme.Keyboard)
    }

    void 'Input is ignored if menu is closed'() {
        setup:
        InputHandler inputhandler = Mock(InputHandler)
        MenuState initialState = Mock(MenuState) {
            getInputHandler() >> inputhandler
        }
        Menu menu = new Menu(userInterface, initialState)
        ClickEvent event = new ClickEvent(1, 1)

        when:
        menu.handleClickEvent(event)

        then:
        0 * inputhandler.handleClickEvent(event)

        when:
        menu.handleControlEvent(ControlEvent.ACTION_PRESSED)

        then:
        0 * inputhandler.handleControlEvent(ControlEvent.ACTION_PRESSED)
    }

    void 'Can store objects at super type'() {
        setup:
        Menu menu = new Menu(userInterface, Mock(MenuState))
        B value = new B()

        when:
        menu.store('B', value, A)

        then:
        menu.getFromStore('B', A).get() == value
    }

    void 'Can store objects at default type'() {
        setup:
        Menu menu = new Menu(userInterface, Mock(MenuState))
        B value = new B()

        when:
        menu.store('B', value)

        then:
        menu.getFromStore('B', B).get() == value
    }

    @SuppressWarnings('EmptyClass')
    private class A { }

    @SuppressWarnings('EmptyClass')
    private class B extends A { }
}
