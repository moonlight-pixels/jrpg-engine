package com.moonlightpixels.jrpg.legacy.map;

import com.moonlightpixels.jrpg.legacy.map.actor.Direction;

public final class WarpTarget {
    public static final Direction DEFAULT_FACING_DIRECTION = Direction.DOWN;

    private final MapDefinition map;
    private final TileCoordinate location;
    private final Direction facing;

    public WarpTarget(final MapDefinition map, final TileCoordinate location, final Direction facing) {
        this.map = map;
        this.location = location;
        this.facing = facing;
    }

    public WarpTarget(final MapDefinition map, final TileCoordinate location) {
        this(map, location, DEFAULT_FACING_DIRECTION);
    }

    public MapDefinition getMap() {
        return map;
    }

    public TileCoordinate getLocation() {
        return location;
    }

    public Direction getFacing() {
        return facing;
    }
}
