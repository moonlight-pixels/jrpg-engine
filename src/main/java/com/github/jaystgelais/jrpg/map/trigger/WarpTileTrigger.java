package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.MapDefinition;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.WarpTarget;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.Direction;

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
