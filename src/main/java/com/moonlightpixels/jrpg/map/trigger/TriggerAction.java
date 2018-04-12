package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.graphics.Renderable;
import com.moonlightpixels.jrpg.input.InputHandler;
import com.moonlightpixels.jrpg.map.MapMode;
import com.moonlightpixels.jrpg.state.Updatable;

public interface TriggerAction extends Renderable, Updatable, InputHandler {
    void startAction(MapMode mapMode);
    boolean isComplete();
    default boolean flushInputOnComplete() {
        return true;
    }
}
