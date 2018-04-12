package com.moonlightpixels.jrpg.state;

import com.moonlightpixels.jrpg.graphics.Renderable;
import com.moonlightpixels.jrpg.input.InputHandler;

import java.util.Map;


public interface State extends Updatable, Renderable, InputHandler {
    String getKey();

    void onEnter(Map<String, Object> params);
    void onExit();
}
