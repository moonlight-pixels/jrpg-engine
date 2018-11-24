package com.moonlightpixels.jrpg.map;

import com.badlogic.gdx.math.Vector2;
import com.moonlightpixels.jrpg.map.internal.MapActor;

/**
 * Interactive Maps in the game.
 */
public interface JRPGMap {
    /**
     * Returns the coordinate of the lower left corner of the tile at a given TileCoordinate.
     *
     * @param tileCoordinate Tile coordinate
     * @return Pixel coordinate
     */
    default Vector2 getTileCoordinateXY(TileCoordinate tileCoordinate) {
        return new Vector2(
            tileCoordinate.getX() * getTileWidth(),
            tileCoordinate.getY() * getTileHeight()
        );
    }

    /**
     * The width of tiles in this map.
     *
     * @return tile width
     */
    float getTileWidth();

    /**
     * The height of tiles in this map.
     *
     * @return tile height
     */
    float getTileHeight();

    /**
     * Updates Actors in Map.
     *
     * @param delta time elapsed (seconds) since last render loop.
     */
    void update(float delta);

    /**
     * Renders map and actors.
     */
    void render();

    /**
     * Adds an actor to the map.
     *
     * @param actor MapActor to add
     */
    void addActor(MapActor<?> actor);
}
