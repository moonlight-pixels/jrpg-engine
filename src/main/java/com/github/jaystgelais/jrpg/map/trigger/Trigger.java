package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.state.StateMachine;

public interface Trigger {
    boolean isTriggered(Actor hero);
    StateMachine performAction(TriggerContext context);
}
