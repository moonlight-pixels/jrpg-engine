package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.actor.Actor;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public final class MapLayer {
    private static final ActorsToRenderComparator ACTORS_TO_RENDER_COMPARATOR = new ActorsToRenderComparator();

    private final int layerIndex;
    private final TiledMapRenderer mapRenderer;
    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer decorationBackgroundLayer;
    private TiledMapTileLayer decorationForegroundLayer;
    private TiledMapTileLayer collisionLayer;

    public MapLayer(final int layerIndex, final TiledMapRenderer mapRenderer) {
        this.layerIndex = layerIndex;
        this.mapRenderer = mapRenderer;
    }

    public TiledMapTileLayer getBaseLayer() {
        return baseLayer;
    }

    public void setBaseLayer(final TiledMapTileLayer baseLayer) {
        this.baseLayer = baseLayer;
    }

    public TiledMapTileLayer getDecorationBackgroundLayer() {
        return decorationBackgroundLayer;
    }

    public void setDecorationBackgroundLayer(final TiledMapTileLayer decorationBackgroundLayer) {
        this.decorationBackgroundLayer = decorationBackgroundLayer;
    }

    public TiledMapTileLayer getDecorationForegroundLayer() {
        return decorationForegroundLayer;
    }

    public void setDecorationForegroundLayer(final TiledMapTileLayer decorationForegroundLayer) {
        this.decorationForegroundLayer = decorationForegroundLayer;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(final TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public void render(final GraphicsService graphicsService, final List<Actor> actors) {
        graphicsService.renderStart();
        if (baseLayer != null) {
            mapRenderer.renderTileLayer(baseLayer);
        }

        if (decorationBackgroundLayer != null) {
            mapRenderer.renderTileLayer(decorationBackgroundLayer);
        }

        actors
            .stream()
            .filter(actor -> actor.getLocation().getMapLayer() == layerIndex)
            .sorted(ACTORS_TO_RENDER_COMPARATOR)
            .forEach(actor -> actor.render(graphicsService));

        if (decorationForegroundLayer != null) {
            mapRenderer.renderTileLayer(decorationForegroundLayer);
        }
        graphicsService.renderEnd();
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

    private static class ActorsToRenderComparator implements Comparator<Actor>, Serializable {
        private static final long serialVersionUID = 8367751493774593999L;

        @Override
        public int compare(final Actor left, final Actor right) {
            // sort in descending order by Y position
            return Float.compare(right.getPositionY(), left.getPositionY());
        }
    }
}
