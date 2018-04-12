package com.moonlightpixels.jrpg.map.animation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.moonlightpixels.jrpg.state.Updatable;

import java.util.HashMap;
import java.util.Map;

public final class TileAnimation implements Updatable {
    public static final String JRPG_TILE_ANIMATION_ID = "jrpg-animation-id";
    public static final String JRPG_TILE_ANIMATION_INDEX = "jrpg-animation-index";
    private final String id;
    private final long timePerFrameMs;
    private final int[] indexOrder;
    private final TiledMap tiledMap;
    private final Map<Integer, TiledMapTile> tileFrames = new HashMap<>();
    private long timeInAnimation = 0L;

    public TileAnimation(final TileAnimationDefinition tileAnimationDefinition, final TiledMap tiledMap) {
        id = tileAnimationDefinition.getId();
        timePerFrameMs = tileAnimationDefinition.getTimePerFrameMs();
        indexOrder = tileAnimationDefinition.getIndexOrder();
        this.tiledMap = tiledMap;
        findTileFrames();
    }

    @Override
    public void update(final long elapsedTime) {
        timeInAnimation = (timeInAnimation + elapsedTime) % getTotalAnimationTimeMs();
        final int frameIndex = (int) (timeInAnimation / timePerFrameMs);
        TiledMapTile currentTile = tileFrames.get(indexOrder[frameIndex]);
        for (MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledMapLayer = (TiledMapTileLayer) layer;
            for (int x = 0; x < tiledMapLayer.getWidth(); x++) {
                for (int y = 0; y < tiledMapLayer.getHeight(); y++) {
                    final TiledMapTileLayer.Cell cell = tiledMapLayer.getCell(x, y);
                    if (cell != null) {
                        TiledMapTile tile = cell.getTile();
                        final MapProperties tileProperties = tile.getProperties();
                        if (tileProperties.containsKey(JRPG_TILE_ANIMATION_ID)
                                && tileProperties.get(JRPG_TILE_ANIMATION_ID).equals(id)) {
                            cell.setTile(currentTile);
                        }
                    }
                }
            }
        }
    }

    public long getTotalAnimationTimeMs() {
        return timePerFrameMs * indexOrder.length;
    }

    private void findTileFrames() {
        for (TiledMapTileSet tileSet : tiledMap.getTileSets()) {
            for (TiledMapTile tile : tileSet) {
                final MapProperties tileProperties = tile.getProperties();
                if (tileProperties.containsKey(JRPG_TILE_ANIMATION_ID)
                        && tileProperties.get(JRPG_TILE_ANIMATION_ID).equals(id)) {
                    tileFrames.put(tileProperties.get(JRPG_TILE_ANIMATION_INDEX, Integer.class), tile);
                }
            }
        }
    }
}
