package com.moonlightpixels.jrpg.legacy.map.trigger;

import com.moonlightpixels.jrpg.legacy.graphics.Renderable;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.map.MapMode;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface TriggerAction extends Renderable, Updatable, InputHandler {
    void startAction(MapMode mapMode);
    boolean isComplete();
    default boolean flushInputOnComplete() {
        return true;
    }
}
