package com.github.jaystgelais.jrpg.map.actor;

public final class InspectAction implements Action {
    @Override
    public void perform(final Actor actor) {
        actor.inspect();
    }
}
