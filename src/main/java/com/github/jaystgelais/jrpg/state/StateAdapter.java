package com.github.jaystgelais.jrpg.state;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;

import java.util.Map;

/**
 * Created by jgelais on 2/21/17.
 */
public abstract class StateAdapter implements State {

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public void handleInput(final InputService inputService) {

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

    @Override
    public void dispose() {

    }
}
