package com.github.jaystgelais.jrpg.state;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;

import java.util.Map;


public interface State extends Updatable, Renderable, InputHandler {
    String getKey();

    void onEnter(Map<String, Object> params);
    void onExit();
}
