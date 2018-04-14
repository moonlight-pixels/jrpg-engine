package com.moonlightpixels.jrpg.legacy.map.trigger;

import com.moonlightpixels.jrpg.legacy.map.MapDefinition;
import com.moonlightpixels.jrpg.legacy.map.actor.Direction;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.TileCoordinate;
import com.moonlightpixels.jrpg.legacy.map.WarpTarget;
import com.moonlightpixels.jrpg.legacy.map.actor.Actor;

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
