package com.github.jaystgelais.jrpg.state;

import com.github.jaystgelais.jrpg.graphics.Renderable;

import java.util.Map;


public interface State extends Updatable, Renderable {
    String getKey();

    void onEnter(Map<String, Object> params);
    void onExit();
}
