package com.moonlightpixels.jrpg.map.actor;

import com.moonlightpixels.jrpg.state.Updatable;

public interface Controller extends Updatable {
    Action nextAction();

    void setActor(Actor actor);
}
