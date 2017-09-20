package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.Actor;

public final class LayerChangeTriggerAction implements TriggerAction {
    private final int layerIndex;
    private boolean isComplete = false;

    public LayerChangeTriggerAction(final int layerIndex) {
        this.layerIndex = layerIndex;
    }

    @Override
    public void startAction(final MapMode mapMode) {
        final Actor hero = mapMode.getHero();
        final TileCoordinate currentLocation = hero.getLocation();
        hero.setLocation(new TileCoordinate(currentLocation.getX(), currentLocation.getY(), layerIndex));
        isComplete = true;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public boolean flushInputOnComplete() {
        return false;
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
    public void dispose() {

    }
}
