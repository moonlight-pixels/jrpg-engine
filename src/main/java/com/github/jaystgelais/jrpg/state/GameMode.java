package com.github.jaystgelais.jrpg.state;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;

import java.util.Map;

public abstract class GameMode implements State {
    public static final String STATE_KEY_PREFIX = "gameMode:";

    public abstract String getName();

    @Override
    public final String getKey() {
        return STATE_KEY_PREFIX + getName();
    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public void render(final GraphicsService graphicsService) {

    }

    @Override
    public void onEnter(final Map<String, Object> params) {

    }

    @Override
    public void onExit() {

    }
}
