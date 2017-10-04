package com.github.jaystgelais.jrpg.map.actor;

import java.util.Collections;
import java.util.Map;

public final class InspectAction implements Action {
    @Override
    public String getActorState() {
        return Actor.STATE_INSPECTING;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Collections.emptyMap();
    }
}
