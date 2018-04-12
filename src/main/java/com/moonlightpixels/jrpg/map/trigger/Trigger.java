package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.map.actor.Actor;

public interface Trigger {
    boolean isTriggered(Actor hero);
    TriggerAction getAction();
}
