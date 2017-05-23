package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.actor.Actor;

import java.util.LinkedList;
import java.util.List;

public final class GameMap {
    static final int[] BACKGROUND_LAYERS = {0};
    static final int[] FOREGROUND_LAYERS = {1};
    public static final String COLLISION_LAYER_NAME = "jrpg:collision";

    private final OrthographicCamera camera;
    private final TiledMap map;
    private final TiledMapRenderer mapRenderer;
    private final List<Actor> actors;
    private Actor focalPoint;

    public GameMap(final OrthographicCamera camera, final TiledMap map, final TiledMapRenderer mapRenderer) {
        this.camera = camera;
        this.map = map;
        this.mapRenderer = mapRenderer;
        actors = new LinkedList<>();
    }

    public Actor getFocalPoint() {
        return focalPoint;
    }

    public void setFocalPoint(final Actor focalPoint) {
        this.focalPoint = focalPoint;
        if (!actors.contains(focalPoint)) {
            actors.add(focalPoint);
        }
    }

    public void addActor(final Actor actor) {
        actors.add(actor);
    }

    public int getMapHeight() {
        return getMapHeightInTiles() * getTileHeight();
    }

    public Integer getMapHeightInTiles() {
        return map.getProperties().get("height", Integer.class);
    }

    public int getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }

    public int getMapWidth() {
        return getMapWidthInTiles() * getTileWidth();
    }

    public Integer getMapWidthInTiles() {
        return map.getProperties().get("width", Integer.class);
    }

    public int getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }

    public float getAbsoluteX(final TileCoordinate coordinate) {
        return coordinate.getX() * getTileWidth();
    }

    public float getAbsoluteY(final TileCoordinate coordinate) {
        return coordinate.getY() * getTileHeight();
    }

    public boolean isCollision(final TileCoordinate coordinate) {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(COLLISION_LAYER_NAME);
        if (collisionLayer != null) {
            final TiledMapTileLayer.Cell cell = collisionLayer.getCell(coordinate.getX(), coordinate.getY());
            if (cell != null) {
                return true;
            }
        }

        return false;
    }

    public void focusCamera() {
        if (focalPoint != null) {
            float focusX = focalPoint.getPositionX();
            if (focusX < camera.viewportWidth / 2) {
                focusX = camera.viewportWidth / 2;
            }
            if (focusX > getMapWidth() - (camera.viewportWidth / 2)) {
                focusX = getMapWidth() - (camera.viewportWidth / 2);
            }

            float focusY = focalPoint.getPositionY();
            if (focusY < camera.viewportHeight / 2) {
                focusY = camera.viewportHeight / 2;
            }
            if (focusY > getMapHeight() - (camera.viewportHeight / 2)) {
                focusY = getMapHeight() - (camera.viewportHeight / 2);
            }

            camera.position.set(focusX, focusY, 0);
        }
        camera.update();
        mapRenderer.setView(camera);
    }

    public void renderBackground(final GraphicsService graphicsService) {
        mapRenderer.render(BACKGROUND_LAYERS);
    }

    public void renderForeground(final GraphicsService graphicsService) {
        mapRenderer.render(FOREGROUND_LAYERS);
    }

    public List<Actor> getActors() {
        return actors;
    }
}
