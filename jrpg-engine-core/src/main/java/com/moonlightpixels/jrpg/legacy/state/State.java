package com.moonlightpixels.jrpg.legacy.state;

import com.moonlightpixels.jrpg.legacy.graphics.Renderable;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;

import java.util.Map;


public interface State extends Updatable, Renderable, InputHandler {
    String getKey();

    void onEnter(Map<String, Object> params);
    void onExit();
}
