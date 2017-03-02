package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.map.actor.Actor;

public final class GameMap implements Renderable {
    private final OrthographicCamera camera;
    private final TiledMap map;
    private final TiledMapRenderer mapRenderer;
    private Actor focalPoint;

    public GameMap(final OrthographicCamera camera, final TiledMap map, final TiledMapRenderer mapRenderer) {
        this.camera = camera;
        this.map = map;
        this.mapRenderer = mapRenderer;
    }

    public Actor getFocalPoint() {
        return focalPoint;
    }

    public void setFocalPoint(final Actor focalPoint) {
        this.focalPoint = focalPoint;
    }

    public int getMapHeight() {
        return map.getProperties().get("height", Integer.class) * getTileHeight();
    }

    public int getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }

    public int getMapWidth() {
        return map.getProperties().get("width", Integer.class) * getTileWidth();
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

    @Override
    public void render(final GraphicsService graphicsService) {
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
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}