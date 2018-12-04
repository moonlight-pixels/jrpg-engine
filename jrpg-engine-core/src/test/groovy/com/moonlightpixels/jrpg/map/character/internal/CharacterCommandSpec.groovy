package com.moonlightpixels.jrpg.map.character.internal

import com.moonlightpixels.jrpg.map.Direction
import spock.lang.Specification
import spock.lang.Unroll

class CharacterCommandSpec extends Specification {
    @Unroll
    void 'getWalkByDirection(#direction) returns #command'(final Direction direction, final CharacterCommand command) {
        expect:
        CharacterCommand.getWalkByDirection(direction) == command

        where:
        direction       | command
        Direction.UP    | CharacterCommand.WalkUp
        Direction.LEFT  | CharacterCommand.WalkLeft
        Direction.RIGHT | CharacterCommand.WalkRight
        Direction.DOWN  | CharacterCommand.WalkDown
    }

    @Unroll
    void 'getDirectionForWalkCommand(#command) returns #direction'(final Direction direction, final CharacterCommand command) {
        expect:
        CharacterCommand.getDirectionForWalkCommand(command) == direction

        where:
        direction       | command
        Direction.UP    | CharacterCommand.WalkUp
        Direction.LEFT  | CharacterCommand.WalkLeft
        Direction.RIGHT | CharacterCommand.WalkRight
        Direction.DOWN  | CharacterCommand.WalkDown
    }
}
