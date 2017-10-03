package com.github.jaystgelais.jrpg.map.actor;

public final class InspectAction implements Action {
    @Override
    public String getActorState() {
        return Actor.STATE_INSPECTING;
    }

    @Override
    public Direction getDirection() {
        return null;
    }
}
