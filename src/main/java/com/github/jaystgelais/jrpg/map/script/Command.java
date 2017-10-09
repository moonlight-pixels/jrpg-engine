package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface Command extends Updatable, Renderable, InputHandler {
    boolean isComplete();
    void start();
}
