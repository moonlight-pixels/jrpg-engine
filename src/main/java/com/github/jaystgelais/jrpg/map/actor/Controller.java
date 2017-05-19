package com.github.jaystgelais.jrpg.map.actor;

import com.github.jaystgelais.jrpg.state.Updatable;

public interface Controller extends Updatable {
    Action nextAction();
}
