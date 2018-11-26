package com.moonlightpixels.jrpg.map.character.internal;

import com.moonlightpixels.jrpg.map.Direction;

public enum CharacterCommand {
    Stand,
    WalkUp,
    WalkLeft,
    WalkRight,
    WalkDown;

    public static CharacterCommand getWalkByDirection(final Direction direction) {
        switch (direction) {
            case UP:
                return WalkUp;
            case LEFT:
                return WalkLeft;
            case RIGHT:
                return WalkRight;
            case DOWN:
                return WalkDown;
            default:
                return Stand;
        }
    }

    public static Direction getDirectionForWalkCommand(final CharacterCommand command) {
        switch (command) {
            case WalkUp:
                return Direction.UP;
            case WalkLeft:
                return Direction.LEFT;
            case WalkRight:
                return Direction.RIGHT;
            case WalkDown:
                return Direction.DOWN;
            default:
                throw new IllegalArgumentException(String.format("%s is not a walk command", command));
        }
    }
}
