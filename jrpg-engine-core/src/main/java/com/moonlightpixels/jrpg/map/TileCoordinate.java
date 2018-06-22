package com.moonlightpixels.jrpg.map;

import lombok.Data;

/**
 * Represents a location on a tile based bap, supporting multiple walkable layers.
 */
@Data
public final class TileCoordinate {
    private final int x;
    private final int y;
    private final int mapLayer;

    /**
     * Constructs a new TileCoordinate from its x, y and mapLayer.
     *
     * @param x Tile x position on the map
     * @param y Tile y poistion on the map
     * @param mapLayer walkable map layer
     */
    public TileCoordinate(final int x, final int y, final int mapLayer) {
        this.x = x;
        this.y = y;
        this.mapLayer = mapLayer;
    }

    /**
     * Convenience constructor for single layer maps. Defaults to mapLayer 0.
     *
     * @param x Tile x position on the map
     * @param y Tile y poistion on the map
     */
    public TileCoordinate(final int x, final int y) {
        this(x, y, 0);
    }

    /**
     * Returns the TileCoordinate above this TileCoordinate.
     *
     * @return TileCoordinate above this TileCoordinate
     */
    public TileCoordinate getAbove() {
        return new TileCoordinate(x, y, mapLayer + 1);
    }

    /**
     * Returns the TileCoordinate below this TileCoordinate.
     *
     * @return TileCoordinate below this TileCoordinate
     */
    public TileCoordinate getBelow() {
        return new TileCoordinate(x, y, mapLayer - 1);
    }

    /**
     * Returns the adjacent TileCoordinate in the given direction.
     *
     * @param direction Direction of adjacent TileCoordinate
     * @return TileCoordinate adjacent to this TileCoordiante in a given direction
     */
    public TileCoordinate getAdjacent(final Direction direction) {
        TileCoordinate adjacent = null;

        if (direction == Direction.UP) {
            adjacent = new TileCoordinate(x, y + 1, mapLayer);
        } else if (direction == Direction.DOWN) {
            adjacent = new TileCoordinate(x, y - 1, mapLayer);
        } else if (direction == Direction.RIGHT) {
            adjacent = new TileCoordinate(x + 1, y, mapLayer);
        } else if (direction == Direction.LEFT) {
            adjacent = new TileCoordinate(x - 1, y, mapLayer);
        }

        return adjacent;
    }
}
