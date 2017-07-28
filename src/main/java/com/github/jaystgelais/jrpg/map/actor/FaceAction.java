package com.github.jaystgelais.jrpg.map.actor;

public final class FaceAction implements Action {
    private final Direction direction;

    public FaceAction(final Direction direction) {
        this.direction = direction;
    }

    @Override
    public void perform(final Actor actor) {
        actor.setFacing(direction);
    }
}
