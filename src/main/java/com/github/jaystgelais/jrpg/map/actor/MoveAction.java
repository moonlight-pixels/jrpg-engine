package com.github.jaystgelais.jrpg.map.actor;


import java.util.Collections;
import java.util.Map;

public final class MoveAction implements Action {
    private final Direction direction;

    public MoveAction(final Direction direction) {
        this.direction = direction;
    }


    @Override
    public String getActorState() {
        return Actor.STATE_WALKING;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Collections.singletonMap(Actor.STATE_PARAM_DIRECTION, direction);
    }
}
