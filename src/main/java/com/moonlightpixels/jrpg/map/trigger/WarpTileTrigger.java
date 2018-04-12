package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.map.GameMap;
import com.moonlightpixels.jrpg.map.MapDefinition;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import com.moonlightpixels.jrpg.map.WarpTarget;
import com.moonlightpixels.jrpg.map.actor.Actor;
import com.moonlightpixels.jrpg.map.actor.Direction;

public final class WarpTileTrigger implements TileTrigger {
    private final WarpTarget warpTarget;

    public WarpTileTrigger(final WarpTarget warpTarget) {
        this.warpTarget = warpTarget;
    }

    public  WarpTileTrigger(final MapDefinition map, final TileCoordinate location, final Direction facing) {
        this.warpTarget = new WarpTarget(map, location, facing);
    }

    public WarpTileTrigger(final MapDefinition map, final TileCoordinate location) {
        this.warpTarget = new WarpTarget(map, location);
    }

    @Override
    public void onEnter(final GameMap map, final Actor actor) {
        if (actor.isHero()) {
            map.queueAction(new WarpTriggerAction(warpTarget));
        }
    }

    @Override
    public void onExit(final GameMap map, final Actor actor) {

    }

    @Override
    public void onInspect(final GameMap map) {

    }
}
