package com.github.jaystgelais.jrpg.map.actor;


public final class MoveAction implements Action {
    private final Direction direction;

    MoveAction(final Direction direction) {
        this.direction = direction;
    }


    @Override
    public String getActorState() {
        return Actor.STATE_WALKING;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
}
