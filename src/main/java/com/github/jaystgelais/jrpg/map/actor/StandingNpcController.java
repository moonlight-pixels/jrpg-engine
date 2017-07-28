package com.github.jaystgelais.jrpg.map.actor;

public final class StandingNpcController implements Controller {
    private final Direction preferredDirection;

    public StandingNpcController(final Direction preferredDirection) {
        this.preferredDirection = preferredDirection;
    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public Action nextAction() {
        return new FaceAction(preferredDirection);
    }
}
