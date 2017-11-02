package com.github.jaystgelais.jrpg.util;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

public final class TiledUtil {
    private static final String FISRTGID_PARAM = "firstgid";
    private TiledUtil() { }

    public static TiledMapTile getTile(final TiledMap tiledMap, final String tilesetName, final int tileId) {
        final TiledMapTileSet tileset = tiledMap.getTileSets().getTileSet(tilesetName);
        return (tileset != null)
                ? tileset.getTile(tileset.getProperties().get(FISRTGID_PARAM, Integer.class) + tileId)
                : null;
    }

    public static boolean isBackground(final TiledMap tiledMap, final int tileLayerIndex, final String layerName) {
        final TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        return (
                mapLayer != null
                        && matchProperty(mapLayer, GameMap.MAP_LAYER_PROP_MAP_LAYER, tileLayerIndex)
                        && matchProperty(
                                mapLayer,
                                GameMap.MAP_LAYER_PROP_LAYER_TYPE,
                                GameMap.MAP_LAYER_TYPE_BACKGRAOUND
                        )
        );
    }

    public static void updateTile(final TiledMap tiledMap, final TileCoordinate location,
                                  final String layerName, final TiledMapTile tile) {
        final TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
        if (mapLayer != null && matchProperty(mapLayer, GameMap.MAP_LAYER_PROP_MAP_LAYER, location.getMapLayer())) {
            mapLayer.getCell(location.getX(), location.getY()).setTile(tile);
        }
    }

    public static <T> boolean matchProperty(final MapLayer mapLayer, final String propertyName,
                                                           final T value) {
        return value.equals(mapLayer.getProperties().get(propertyName, value.getClass()));
    }

    public static <T> T getCellPropertyFromTopMostTile(final TiledMap tiledMap, final TileCoordinate coordinate,
                                                       final String propertyName, final Class<T> clazz) {
        T value = null;
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            if (mapLayer instanceof TiledMapTileLayer
                    && matchProperty(mapLayer, GameMap.MAP_LAYER_PROP_MAP_LAYER, coordinate.getMapLayer())) {
                TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) mapLayer;
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(coordinate.getX(), coordinate.getY());
                if (cell != null) {
                    final MapProperties cellProps = cell.getTile().getProperties();
                    value = (cellProps.get(propertyName, clazz) != null) ? cellProps.get(propertyName, clazz) : value;
                }
            }
        }

        return value;
    }

}
