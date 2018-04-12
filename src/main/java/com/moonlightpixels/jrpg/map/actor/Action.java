package com.moonlightpixels.jrpg.map.actor;

import java.util.Map;

public interface Action {
    String getActorState();
    Map<String, Object> getParameters();
}
