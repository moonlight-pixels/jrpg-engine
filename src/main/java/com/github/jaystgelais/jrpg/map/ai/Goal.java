package com.github.jaystgelais.jrpg.map.ai;

import com.github.jaystgelais.jrpg.map.actor.Action;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface Goal extends Updatable {
    boolean isComplete(final Actor actor);
    Action nextAction(final Actor actor);
}
