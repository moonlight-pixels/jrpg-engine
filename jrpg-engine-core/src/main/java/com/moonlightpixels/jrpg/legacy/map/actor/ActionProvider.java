package com.moonlightpixels.jrpg.legacy.map.actor;

public interface ActionProvider {
    Action getNextAction();
    boolean hasNextAction();
}
