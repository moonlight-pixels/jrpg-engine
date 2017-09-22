package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.actor.Actor;

public final class LayerChangeTileTrigger implements TileTrigger {
    private final int layerIndex;

    public LayerChangeTileTrigger(final int layerIndex) {
        this.layerIndex = layerIndex;
    }

    @Override
    public void onEnter(final GameMap map, final Actor actor) {
        final TileCoordinate currentLocation = actor.getLocation();
        actor.setLocation(new TileCoordinate(currentLocation.getX(), currentLocation.getY(), layerIndex));
    }

    @Override
    public void onExit(final GameMap map, final Actor actor) {

    }

    @Override
    public void onInspect(final GameMap map) {

    }
}
