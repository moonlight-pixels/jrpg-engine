package com.github.jaystgelais.jrpg.map.actor;

import java.util.Collections;
import java.util.Map;

public final class FaceAction implements Action {
    private final Direction direction;

    public FaceAction(final Direction direction) {
        this.direction = direction;
    }

    @Override
    public String getActorState() {
        return Actor.STATE_STANDING;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Collections.singletonMap(Actor.STATE_PARAM_DIRECTION, direction);
    }
}
