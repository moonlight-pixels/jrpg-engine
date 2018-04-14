package com.moonlightpixels.jrpg.legacy.map.trigger;

import com.moonlightpixels.jrpg.legacy.map.actor.Actor;

public interface Trigger {
    boolean isTriggered(Actor hero);
    TriggerAction getAction();
}
