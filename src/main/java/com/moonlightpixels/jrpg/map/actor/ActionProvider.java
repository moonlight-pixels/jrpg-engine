package com.moonlightpixels.jrpg.map.actor;

public interface ActionProvider {
    Action getNextAction();
    boolean hasNextAction();
}
