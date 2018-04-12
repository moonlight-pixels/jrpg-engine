package com.moonlightpixels.jrpg.map.script;

import com.moonlightpixels.jrpg.graphics.Renderable;
import com.moonlightpixels.jrpg.input.InputHandler;
import com.moonlightpixels.jrpg.state.Updatable;

public interface Command extends Updatable, Renderable, InputHandler {
    boolean isComplete();
    void start();
}
