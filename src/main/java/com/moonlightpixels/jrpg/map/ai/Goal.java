package com.moonlightpixels.jrpg.map.ai;

import com.moonlightpixels.jrpg.map.actor.Action;
import com.moonlightpixels.jrpg.map.actor.Actor;
import com.moonlightpixels.jrpg.state.Updatable;

public interface Goal extends Updatable {
    boolean isAchievable(final Actor actor);
    boolean isComplete(final Actor actor);
    Action nextAction(final Actor actor);
}
