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
        return new TileCoordinate(x, y + 1, mapLayer);
    }

    /**
     * Returns the TileCoordinate below this TileCoordinate.
     *
     * @return TileCoordinate below this TileCoordinate
     */
    public TileCoordinate getBelow() {
        return new TileCoordinate(x, y - 1, mapLayer);
    }

    /**
     * Returns the TileCoordinate to the left of this TileCoordinate.
     *
     * @return TileCoordinate to the left of this TileCoordinate
     */
    public TileCoordinate getLeft() {
        return new TileCoordinate(x - 1, y, mapLayer);
    }

    /**
     * Returns the TileCoordinate to the right of this TileCoordinate.
     *
     * @return TileCoordinate to the right of this TileCoordinate
     */
    public TileCoordinate getRight() {
        return new TileCoordinate(x + 1, y, mapLayer);
    }
}
