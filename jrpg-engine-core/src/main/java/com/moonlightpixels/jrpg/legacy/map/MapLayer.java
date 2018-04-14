package com.moonlightpixels.jrpg.legacy.map;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class MapLayer {
    private static final EntitiesToRenderComparator ENTITIES_TO_RENDER_COMPARATOR = new EntitiesToRenderComparator();

    private final int layerIndex;
    private final TiledMapRenderer mapRenderer;
    private final List<TiledMapTileLayer> backgroundLayers = new LinkedList<>();
    private final List<TiledMapTileLayer> foregroundLayers = new LinkedList<>();
    private TiledMapTileLayer collisionLayer;

    public MapLayer(final int layerIndex, final TiledMapRenderer mapRenderer) {
        this.layerIndex = layerIndex;
        this.mapRenderer = mapRenderer;
    }

    public void addBackgroundLayer(final TiledMapTileLayer tileLayer) {
        backgroundLayers.add(tileLayer);
    }

    public void addForegroundLayer(final TiledMapTileLayer tileLayer) {
        foregroundLayers.add(tileLayer);
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(final TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public void render(final GraphicsService graphicsService, final List<Entity> entities) {
        backgroundLayers.forEach(mapRenderer::renderTileLayer);

        entities
            .stream()
            .filter(entity -> entity.getLocation().getMapLayer() == layerIndex)
            .sorted(ENTITIES_TO_RENDER_COMPARATOR)
            .forEach(entity -> entity.render(graphicsService));

        foregroundLayers.forEach(mapRenderer::renderTileLayer);
    }

    public boolean isCollision(final TileCoordinate coordinate) {
        if (collisionLayer != null) {
            final TiledMapTileLayer.Cell cell = collisionLayer.getCell(coordinate.getX(), coordinate.getY());
            if (cell != null) {
                return true;
            }
        }

        return false;
    }

    public boolean isBackground(final String layerName) {
        return backgroundLayers.stream().anyMatch(layer -> layerName.equals(layer.getName()));
    }

    private static class EntitiesToRenderComparator implements Comparator<Entity>, Serializable {
        private static final long serialVersionUID = 8367751493774593999L;

        @Override
        public int compare(final Entity left, final Entity right) {
            // sort in descending order by Y position
            return Float.compare(right.getPositionY(), left.getPositionY());
        }
    }
}
