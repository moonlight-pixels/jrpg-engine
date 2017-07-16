package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface TriggerAction extends Renderable, Updatable, InputHandler {
    void startAction(MapMode mapMode);
    boolean isComplete();
}
