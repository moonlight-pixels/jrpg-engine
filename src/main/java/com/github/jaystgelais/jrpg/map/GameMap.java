package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.trigger.TileTrigger;
import com.github.jaystgelais.jrpg.map.trigger.Trigger;
import com.github.jaystgelais.jrpg.map.trigger.TriggerAction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;

public final class GameMap implements Renderable {
    static final int[] BACKGROUND_LAYERS = {0};
    static final int[] FOREGROUND_LAYERS = {1};
    public static final String MAP_LAYER_PROP_MAP_LAYER = "jrpg-map-layer";
    public static final String MAP_LAYER_PROP_LAYER_TYPE = "jrpg-layer-type";
    public static final String MAP_LAYER_TYPE_BACKGRAOUND = "background";
    public static final String MAP_LAYER_TYPE_FOREGRAOUND = "foreground";
    public static final String MAP_LAYER_TYPE_COLLISION = "collision";

    private final OrthographicCamera camera;
    private final TiledMap map;
    private final TiledMapRenderer mapRenderer;
    private final SortedMap<Integer, MapLayer> mapLayers;
    private final List<Actor> actors;
    private Actor focalPoint;
    private final List<Trigger> triggers;
    private final Map<TileCoordinate, TileTrigger> tileTriggers;
    private final Queue<TriggerAction> actionQueue;

    public GameMap(final OrthographicCamera camera, final TiledMap map, final TiledMapRenderer mapRenderer) {
        this.camera = camera;
        this.map = map;
        this.mapRenderer = mapRenderer;
        mapLayers = new TreeMap<>();
        actors = new LinkedList<>();
        triggers = new LinkedList<>();
        tileTriggers = new HashMap<>();
        actionQueue = new LinkedList<>();
        buildMapLayers(map);
    }

    private void buildMapLayers(final TiledMap map) {
        for (com.badlogic.gdx.maps.MapLayer mapLayer : map.getLayers()) {
            TiledMapTileLayer tiledMapLayer = (TiledMapTileLayer) mapLayer;

            if (tiledMapLayer.getProperties().containsKey(MAP_LAYER_PROP_MAP_LAYER)) {
                int mapLayerIndex = tiledMapLayer.getProperties().get(MAP_LAYER_PROP_MAP_LAYER, Integer.class);

                MapLayer gameMapLayer = mapLayers.computeIfAbsent(mapLayerIndex, i -> new MapLayer(i, mapRenderer));

                switch (tiledMapLayer.getProperties().get(MAP_LAYER_PROP_LAYER_TYPE, String.class)) {
                    case MAP_LAYER_TYPE_BACKGRAOUND:
                        gameMapLayer.addBackgroundLayer(tiledMapLayer);
                        break;
                    case MAP_LAYER_TYPE_FOREGRAOUND:
                        gameMapLayer.addForegroundLayer(tiledMapLayer);
                        break;
                    case MAP_LAYER_TYPE_COLLISION:
                        gameMapLayer.setCollisionLayer(tiledMapLayer);
                        break;
                    default:
                }
            }
        }
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

    public void addTrigger(final Trigger trigger) {
        triggers.add(trigger);
    }

    public void addTileTrigger(final TileCoordinate coordinate, final TileTrigger tileTrigger) {
        tileTriggers.put(coordinate, tileTrigger);
    }

    public List<Trigger> getTriggers() {
        return triggers;
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

    public boolean isCollision(final Actor actor, final TileCoordinate coordinate) {
        for (Actor otherActor : actors) {
            if (actor != otherActor && otherActor.isOccupying(coordinate)) {
                return true;
            }
        }
        return mapLayers.get(coordinate.getMapLayer()).isCollision(coordinate);
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

    public void fireOnExitTrigger(final TileCoordinate coordinate) {
        if (tileTriggers.containsKey(coordinate) && tileTriggers.get(coordinate).onExit() != null) {
            actionQueue.add(tileTriggers.get(coordinate).onExit());
        }
    }

    public void fireOnEnterTrigger(final TileCoordinate coordinate) {
        if (tileTriggers.containsKey(coordinate) && tileTriggers.get(coordinate).onEnter() != null) {
            actionQueue.add(tileTriggers.get(coordinate).onEnter());
        }
    }

    public void fireOnInspectTrigger(final TileCoordinate coordinate) {
        if (tileTriggers.containsKey(coordinate) && tileTriggers.get(coordinate).onInspect() != null) {
            actionQueue.add(tileTriggers.get(coordinate).onInspect());
        }
    }

    public TriggerAction getNextTriggeredAction() {
        return actionQueue.poll();
    }

    List<Actor> getActors() {
        return actors;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        mapLayers.keySet().forEach(mapLayerIndex -> {
            mapLayers.get(mapLayerIndex).render(graphicsService, actors);
        });
    }

    @Override
    public void dispose() {

    }
}
