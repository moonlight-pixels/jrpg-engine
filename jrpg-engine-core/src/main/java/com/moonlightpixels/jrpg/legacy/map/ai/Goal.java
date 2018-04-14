package com.moonlightpixels.jrpg.legacy.map.ai;

import com.moonlightpixels.jrpg.legacy.map.actor.Actor;
import com.moonlightpixels.jrpg.legacy.state.Updatable;
import com.moonlightpixels.jrpg.legacy.map.actor.Action;

public interface Goal extends Updatable {
    boolean isAchievable(final Actor actor);
    boolean isComplete(final Actor actor);
    Action nextAction(final Actor actor);
}
