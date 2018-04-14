package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.graphics.Renderable;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface Command extends Updatable, Renderable, InputHandler {
    boolean isComplete();
    void start();
}
