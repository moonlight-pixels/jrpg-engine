package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.map.actor.Actor;

public interface Trigger {
    boolean isTriggered(Actor hero);
    TriggerAction getAction();
}
