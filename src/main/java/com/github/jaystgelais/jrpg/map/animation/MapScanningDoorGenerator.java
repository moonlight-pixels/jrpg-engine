package com.github.jaystgelais.jrpg.map.animation;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class MapScanningDoorGenerator {
    private static final String JRPG_TILE_IS_DOOR = "jrpg-is-door";

    private final Door.OpenActions openAction;
    private final DoorLockCheck lockCheck;
    private final long animationTimeMs;
    private int doorCounter = 0;


    public MapScanningDoorGenerator(final Door.OpenActions openAction, final DoorLockCheck lockCheck,
                                    final long animationTimeMs) {
        this.openAction = openAction;
        this.lockCheck = lockCheck;
        this.animationTimeMs = animationTimeMs;
    }

    public void createDoors(final GameMap map) {
        final Set<MapTile> doorTiles = findDoorTiles(map);
        final Map<String, TiledMapTile[]> animations = buildDoorAnimations(map, getDistinctAnimationIds(doorTiles));
        animations.keySet().forEach(animationId -> {
            System.out.printf("%s(%d)%n", animationId, animations.get(animationId).length);
        });
        final Set<Set<MapTile>> tileClusters = new HashSet<>();
        doorTiles.forEach(doorTile -> {
            Set<MapTile> cluster = new HashSet<>();
            cluster.add(doorTile);

            for (MapTile mapTile : doorTiles) {
                if (!doorTile.equals(mapTile) && isAdjacent(doorTile, mapTile)) {
                    cluster.add(mapTile);
                }
            }

            tileClusters.add(cluster);
        });

        tileClusters.forEach(cluster -> {
            map.addDoor(
                    new Door(
                            getNextDoorId(map),
                            openAction,
                            lockCheck,
                            animationTimeMs,
                            cluster.stream().map(mapTile -> new Door.DoorTile(
                                    map,
                                    mapTile.getLayerName(),
                                    mapTile.getCoordinate(),
                                    animations.get(
                                            mapTile
                                                    .getTile()
                                                    .getProperties()
                                                    .get(TileAnimation.JRPG_TILE_ANIMATION_ID, String.class)
                                    )
                            )).collect(Collectors.toList()).toArray(new Door.DoorTile[0])
                    )
            );
        });
    }

    private boolean isAdjacent(final MapTile doorTile, final MapTile mapTile) {
        return doorTile.getCoordinate().equals(mapTile.getCoordinate().getAbove())
                || doorTile.getCoordinate().equals(mapTile.getCoordinate().getBelow())
                || doorTile.getCoordinate().equals(mapTile.getCoordinate().getLeft())
                || doorTile.getCoordinate().equals(mapTile.getCoordinate().getRight());
    }

    private List<String> getDistinctAnimationIds(final Set<MapTile> doorTiles) {
        return doorTiles.stream()
                .map(MapTile::getTile)
                .map(tile -> tile.getProperties().get(TileAnimation.JRPG_TILE_ANIMATION_ID, String.class))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private Set<MapTile> findDoorTiles(final GameMap map) {
        final Set<MapTile> doorTiles = new HashSet<>();
        map.getTiledMap().getLayers().forEach(mapLayer -> {
            if (mapLayer instanceof TiledMapTileLayer) {
                doorTiles.addAll(getDoorTiles((TiledMapTileLayer) mapLayer));
            }
        });

        return doorTiles;
    }

    private Map<String, TiledMapTile[]> buildDoorAnimations(final GameMap map, final List<String> animationIds) {
        final Map<String, Map<Integer, TiledMapTile>> animationTiles = new HashMap<>();
        map.getTiledMap().getTileSets().forEach(tileSet -> {
            tileSet.forEach((TiledMapTile tile) -> {
                final MapProperties tileProperties = tile.getProperties();
                final String animationId = tileProperties.get(TileAnimation.JRPG_TILE_ANIMATION_ID, String.class);
                if (animationIds.contains(animationId)) {
                    int animationIndex = tileProperties.get(TileAnimation.JRPG_TILE_ANIMATION_INDEX, Integer.class);
                    animationTiles.computeIfAbsent(animationId, key -> new TreeMap<>()).put(animationIndex, tile);
                }
            });
        });

        final Map<String, TiledMapTile[]> animations = new HashMap<>();
        animationTiles.keySet().forEach(animationId -> {
            animations.put(animationId, animationTiles.get(animationId).values().toArray(new TiledMapTile[0]));
        });

        return animations;
    }

    private List<MapTile> getDoorTiles(final TiledMapTileLayer layer) {
        final List<MapTile> mapTiles = new LinkedList<>();
        final int layerIndex = layer.getProperties().get(GameMap.MAP_LAYER_PROP_MAP_LAYER, 0, Integer.class);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                TiledMapTile tile = (cell != null) ? cell.getTile() : null;
                if (tile != null && tile.getProperties().get(JRPG_TILE_IS_DOOR, true, Boolean.class)) {
                    mapTiles.add(new MapTile(
                            new TileCoordinate(x, y, layerIndex),
                            layer.getName(),
                            tile
                    ));
                }
            }
        }

        return mapTiles;
    }

    private String getNextDoorId(final GameMap map) {
        return String.format("%s::%d", map.getClass().getName(), doorCounter++);
    }

    private static final class MapTile {
        private final TileCoordinate coordinate;
        private final String layerName;
        private final TiledMapTile tile;

        private MapTile(final TileCoordinate coordinate, final String layerName, final TiledMapTile tile) {
            this.coordinate = coordinate;
            this.layerName = layerName;
            this.tile = tile;
        }

        public TileCoordinate getCoordinate() {
            return coordinate;
        }

        public String getLayerName() {
            return layerName;
        }

        public TiledMapTile getTile() {
            return tile;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final MapTile mapTile = (MapTile) o;
            return Objects.equals(coordinate, mapTile.coordinate)
                    && Objects.equals(layerName, mapTile.layerName)
                    && Objects.equals(tile, mapTile.tile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordinate, layerName, tile);
        }
    }
}
