package com.moonlightpixels.jrpg.map.character.internal

import com.moonlightpixels.jrpg.input.ClickEvent
import com.moonlightpixels.jrpg.input.ControlEvent
import com.moonlightpixels.jrpg.input.InputScheme
import com.moonlightpixels.jrpg.map.character.CharacterActor
import spock.lang.Specification
import spock.lang.Unroll

class PlayerInputCharacterControllerSpec extends Specification {
    @Unroll
    @SuppressWarnings('LineLength')
    void 'Holding #controlEvent puts causes controller to respond with command #command'(final ControlEvent controlEvent,
                                                                                         final CharacterCommand command) {
        setup:
        PlayerInputCharacterController controller = new PlayerInputCharacterController()

        when:
        controller.handleControlEvent(controlEvent)

        then:
        controller.getNextCommand(Mock(CharacterActor)) == command

        where:
        controlEvent               | command
        ControlEvent.UP_PRESSED    | CharacterCommand.WalkUp
        ControlEvent.LEFT_PRESSED  | CharacterCommand.WalkLeft
        ControlEvent.RIGHT_PRESSED | CharacterCommand.WalkRight
        ControlEvent.DOWN_PRESSED  | CharacterCommand.WalkDown
    }

    @Unroll
    @SuppressWarnings('LineLength')
    void 'Releasing a direction results in stand command [#pressEvent/#releaseEvent]'(final ControlEvent pressEvent,
                                                                                      final ControlEvent releaseEvent,
                                                                                      final CharacterCommand walkCommand) {
        setup:
        PlayerInputCharacterController controller = new PlayerInputCharacterController()

        when:
        controller.handleControlEvent(pressEvent)

        then:
        controller.getNextCommand(Mock(CharacterActor)) == walkCommand

        when:
        controller.handleControlEvent(releaseEvent)

        then:
        controller.getNextCommand(Mock(CharacterActor)) == CharacterCommand.Stand

        where:
        pressEvent                 | releaseEvent                | walkCommand
        ControlEvent.UP_PRESSED    | ControlEvent.UP_RELEASED    | CharacterCommand.WalkUp
        ControlEvent.LEFT_PRESSED  | ControlEvent.LEFT_RELEASED  | CharacterCommand.WalkLeft
        ControlEvent.RIGHT_PRESSED | ControlEvent.RIGHT_RELEASED | CharacterCommand.WalkRight
        ControlEvent.DOWN_PRESSED  | ControlEvent.DOWN_RELEASED  | CharacterCommand.WalkDown
    }

    void 'Currently ignores non-controller input'() {
        setup:
        PlayerInputCharacterController controller = new PlayerInputCharacterController()

        when:
        controller.setInputScheme(InputScheme.TouchMouse)

        then:
        noExceptionThrown()
        !controller.handleClickEvent(new ClickEvent(1, 1))
    }
}
