package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.input.InputService;
import com.moonlightpixels.jrpg.map.MapDefinition;
import com.moonlightpixels.jrpg.map.MapMode;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import com.moonlightpixels.jrpg.map.WarpTarget;
import com.moonlightpixels.jrpg.map.actor.Direction;

import java.util.HashMap;
import java.util.Map;

public final class WarpTriggerAction implements TriggerAction {
    private final WarpTarget warpTarget;

    public WarpTriggerAction(final WarpTarget warpTarget) {
        this.warpTarget = warpTarget;
    }

    public WarpTriggerAction(final MapDefinition map, final TileCoordinate location,
                             final Direction facing) {
        this(new WarpTarget(map, location, facing));
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
        params.put("map", warpTarget.getMap());
        params.put("location", warpTarget.getLocation());
        params.put("facing", warpTarget.getFacing());
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
