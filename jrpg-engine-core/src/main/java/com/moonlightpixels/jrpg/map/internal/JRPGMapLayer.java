package com.moonlightpixels.jrpg.map.internal;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;
import com.moonlightpixels.jrpg.map.TileCoordinate;
import lombok.Getter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

final class JRPGMapLayer {
    private static final ActorRenderOrderComparator ACTOR_RENDER_ORDER_COMPARATOR = new ActorRenderOrderComparator();

    @Getter
    private final int layerIndex;
    private final TiledMapRenderer mapRenderer;
    private final Stage stage;
    private final List<TiledMapTileLayer> backgroundLayers = new LinkedList<>();
    private final List<TiledMapTileLayer> foregroundLayers = new LinkedList<>();
    private TiledMapTileLayer collisionLayer;

    JRPGMapLayer(final int layerIndex, final TiledMapRenderer mapRenderer, final Stage stage) {
        this.layerIndex = layerIndex;
        this.mapRenderer = mapRenderer;
        this.stage = stage;
    }

    void addBackgroundLayer(final TiledMapTileLayer tileLayer) {
        backgroundLayers.add(tileLayer);
    }

    void addForegroundLayer(final TiledMapTileLayer tileLayer) {
        foregroundLayers.add(tileLayer);
    }

    Optional<TiledMapTileLayer> getCollisionLayer() {
        return Optional.ofNullable(collisionLayer);
    }

    void setCollisionLayer(final TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    void addActor(final Actor actor) {
        stage.addActor(actor);
    }

    void removeActor(final Actor actor) {
        stage.getRoot().removeActor(actor);
    }

    void update(final float delta) {
        stage.act(delta);
        sortActors();
    }

    void render(final GraphicsContext graphicsContext) {
        graphicsContext.getSpriteBatch().begin();
        backgroundLayers.forEach(mapRenderer::renderTileLayer);
        graphicsContext.getSpriteBatch().end();

        stage.draw();

        graphicsContext.getSpriteBatch().begin();
        foregroundLayers.forEach(mapRenderer::renderTileLayer);
        graphicsContext.getSpriteBatch().end();
    }

    private void sortActors() {
       Array<Actor> actors = stage.getActors();
       actors.sort(ACTOR_RENDER_ORDER_COMPARATOR);
       for (int i = 0; i < actors.size; i++) {
            actors.get(i).setZIndex(i);
       }
    }

    boolean isOpen(final TileCoordinate tileCoordinate) {
        return getCollisionLayer()
            .map(tiledMapTileLayer -> tiledMapTileLayer.getCell(tileCoordinate.getX(), tileCoordinate.getY()) == null)
            .orElse(true);
    }

    private static class ActorRenderOrderComparator implements Comparator<Actor>, Serializable {
        private static final long serialVersionUID = 4352090894431376885L;

        @Override
        public int compare(final Actor left, final Actor right) {
            // sort in descending order by Y position
            return Float.compare(right.getY(), left.getY());
        }
    }
}
