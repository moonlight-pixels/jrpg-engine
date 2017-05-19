package com.github.jaystgelais.jrpg.map.actor;


public final class MoveAction implements Action {
    private final Direction direction;

    public MoveAction(final Direction direction) {
        this.direction = direction;
    }

    @Override
    public void perform(final Actor actor) {
        actor.setFacing(direction);
        actor.walk(direction);
    }
}
