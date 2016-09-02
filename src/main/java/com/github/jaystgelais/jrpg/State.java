package com.github.jaystgelais.jrpg;

import java.util.Map;

public interface State {
    String getKey();
    void update(final long elapsedTime);
    void render();
    void onEnter(Map<String, Object> params);
    void onExit();
}
