package com.github.jaystgelais.jrpg.util;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

public final class TiledUtil {
    private TiledUtil() { }

    public static TiledMapTile getTile(final TiledMap tiledMap, final String tilesetName, final int tileId) {
        final TiledMapTileSet tileset = tiledMap.getTileSets().getTileSet(tilesetName);
        return (tileset != null) ? tileset.getTile(tileId) : null;
    }

    public static void updateTile(final TiledMap tiledMap, final TileCoordinate location,
                                  final String layerName, final TiledMapTile tile) {
        final TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        if (mapLayer != null && matchProperty(mapLayer, GameMap.MAP_LAYER_PROP_MAP_LAYER, location.getMapLayer())) {
            mapLayer.getCell(location.getX(), location.getY()).setTile(tile);
        }
    }

    public static <T extends Object> boolean matchProperty(final MapLayer mapLayer, final String propertyName,
                                                           final T value) {
        return value.equals(mapLayer.getProperties().get(propertyName, value.getClass()));
    }

}
