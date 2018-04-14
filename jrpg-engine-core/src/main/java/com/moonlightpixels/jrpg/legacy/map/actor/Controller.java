package com.moonlightpixels.jrpg.legacy.map.actor;

import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface Controller extends Updatable {
    Action nextAction();

    void setActor(Actor actor);
}
