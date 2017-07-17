package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.MapDefinition;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

import java.util.HashMap;
import java.util.Map;

public final class WarpTriggerAction implements TriggerAction {
    private final MapDefinition map;
    private final TileCoordinate location;

    public WarpTriggerAction(final MapDefinition map, final TileCoordinate location) {
        this.map = map;
        this.location = location;
    }

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
    public void startAction(final MapMode mapMode) {
        Map<String, Object> params = new HashMap<>();
        params.put("map", map);
        params.put("location", location);
        mapMode.onEnter(params);
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void dispose() {

    }
}
