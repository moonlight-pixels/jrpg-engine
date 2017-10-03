package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.animation.TileAnimation;
import com.github.jaystgelais.jrpg.map.animation.TileAnimationDefinition;
import com.github.jaystgelais.jrpg.map.fx.MapEffect;
import com.github.jaystgelais.jrpg.map.trigger.TileTrigger;
import com.github.jaystgelais.jrpg.map.trigger.Trigger;
import com.github.jaystgelais.jrpg.map.trigger.TriggerAction;
import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;

public final class GameMap implements Renderable, Updatable, Graph<TileCoordinate> {
    public static final String MAP_LAYER_PROP_MAP_LAYER = "jrpg-map-layer";
    public static final String MAP_LAYER_PROP_LAYER_TYPE = "jrpg-layer-type";
    public static final String MAP_LAYER_TYPE_BACKGRAOUND = "background";
    public static final String MAP_LAYER_TYPE_FOREGRAOUND = "foreground";
    public static final String MAP_LAYER_TYPE_COLLISION = "collision";

    private final OrthographicCamera camera;
    private final TiledMap map;
    private final TiledMapRenderer mapRenderer;
    private final SortedMap<Integer, MapLayer> mapLayers;
    private final List<Entity> entities;
    private Actor focalPoint;
    private final List<Trigger> triggers;
    private final Map<TileCoordinate, TileTrigger> tileTriggers;
    private final Queue<TriggerAction> actionQueue;
    private final List<TileAnimation> animations;
    private final List<MapEffect> mapEffects;
    private final Location parentLocation;
    private boolean areEffectsInitialized = false;

    public GameMap(final OrthographicCamera camera, final TiledMap map,
                   final TiledMapRenderer mapRenderer, final Location parentLocation) {
        this.camera = camera;
        this.map = map;
        this.mapRenderer = mapRenderer;
        this.parentLocation = parentLocation;
        mapLayers = new TreeMap<>();
        entities = new LinkedList<>();
        triggers = new LinkedList<>();
        tileTriggers = new HashMap<>();
        actionQueue = new LinkedList<>();
        animations = new LinkedList<>();
        mapEffects = new LinkedList<>();
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
        if (!entities.contains(focalPoint)) {
            entities.add(focalPoint);
        }
    }

    public void addEntity(final Entity entity) {
        entities.add(entity);
    }

    public void addMapEffect(final MapEffect mapEffect) {
        mapEffects.add(mapEffect);
    }

    public Entity getEntity(final TileCoordinate location) {
        for (Entity entity : entities) {
            if (entity.getLocation().equals(location)) {
                return entity;
            }
        }

        return null;
    }

    public void addTrigger(final Trigger trigger) {
        triggers.add(trigger);
    }

    public void addTileTrigger(final TileCoordinate coordinate, final TileTrigger tileTrigger) {
        tileTriggers.put(coordinate, tileTrigger);
    }

    public void addAnimation(final TileAnimationDefinition animationDefinition) {
        animations.add(new TileAnimation(animationDefinition, map));
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

    public int getAbsoluteX(final TileCoordinate coordinate) {
        return coordinate.getX() * getTileWidth() + (getTileWidth() / 2);
    }

    public int getAbsoluteY(final TileCoordinate coordinate) {
        return coordinate.getY() * getTileHeight();
    }

    public boolean isCollision(final Actor actor, final TileCoordinate coordinate) {
        for (Entity entity : entities) {
            if (actor != entity && entity.isOccupying(coordinate)) {
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

    public void queueAction(final TriggerAction action) {
        actionQueue.add(action);
    }

    public void fireOnExitTrigger(final TileCoordinate coordinate, final Actor actor) {
        if (tileTriggers.containsKey(coordinate)) {
            tileTriggers.get(coordinate).onExit(this, actor);
        }
    }

    public void fireOnEnterTrigger(final TileCoordinate coordinate, final Actor actor) {
        if (tileTriggers.containsKey(coordinate)) {
            tileTriggers.get(coordinate).onEnter(this, actor);
        }
    }

    public void fireOnInspectTrigger(final TileCoordinate coordinate) {
        if (tileTriggers.containsKey(coordinate)) {
            tileTriggers.get(coordinate).onInspect(this);
        }
    }

    public TriggerAction getNextTriggeredAction() {
        return actionQueue.poll();
    }

    List<Entity> getEntities() {
        return entities;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        if (!areEffectsInitialized) {
            mapEffects.forEach(mapEffect -> mapEffect.init(graphicsService, this));
            areEffectsInitialized = true;
        }
        mapEffects.forEach(mapEffect -> mapEffect.preRender(graphicsService));

        graphicsService.renderStart();
        mapEffects.forEach(mapEffect -> mapEffect.preMapRender(graphicsService));

        mapLayers.keySet().forEach(mapLayerIndex -> {
            mapLayers.get(mapLayerIndex).render(graphicsService, entities);
        });

        mapEffects.forEach(mapEffect -> mapEffect.postMapRender(graphicsService));
        graphicsService.renderEnd();

        mapEffects.forEach(mapEffect -> mapEffect.postRender(graphicsService));
    }

    @Override
    public void dispose() {
        mapEffects.forEach(Disposable::dispose);
    }

    @Override
    public void update(final long elapsedTime) {
        animations.forEach(animation -> animation.update(elapsedTime));
        mapEffects.forEach(effect -> effect.update(elapsedTime));
    }

    @Override
    public Array<Connection<TileCoordinate>> getConnections(final TileCoordinate fromNode) {
        Array<Connection<TileCoordinate>> connections = new Array<>();

        // Find paths above node
        TileCoordinate currentNode = fromNode;
        int cost = 2;
        while (!isCollision(null, currentNode.getAbove())) {
            connections.add(new TileCoordinateConnection(currentNode, currentNode.getAbove(), cost++));
            currentNode = currentNode.getAbove();
        }

        // Find paths below node
        currentNode = fromNode;
        cost = 2;
        while (!isCollision(null, currentNode.getBelow())) {
            connections.add(new TileCoordinateConnection(currentNode, currentNode.getBelow(), cost++));
            currentNode = currentNode.getBelow();
        }

        // Find paths to the right of node
        currentNode = fromNode;
        cost = 2;
        while (!isCollision(null, currentNode.getRight())) {
            connections.add(new TileCoordinateConnection(currentNode, currentNode.getRight(), cost++));
            currentNode = currentNode.getRight();
        }


        // Find paths to the left of node
        currentNode = fromNode;
        cost = 2;
        while (!isCollision(null, currentNode.getLeft())) {
            connections.add(new TileCoordinateConnection(currentNode, currentNode.getLeft(), cost++));
            currentNode = currentNode.getLeft();
        }

        return connections;
    }

    private static final class TileCoordinateConnection implements Connection<TileCoordinate> {
        private final TileCoordinate fromNode;
        private final TileCoordinate toNode;
        private final float cost;

        private TileCoordinateConnection(final TileCoordinate fromNode, final TileCoordinate toNode, final float cost) {
            this.fromNode = fromNode;
            this.toNode = toNode;
            this.cost = cost;
        }

        @Override
        public float getCost() {
            return cost;
        }

        @Override
        public TileCoordinate getFromNode() {
            return fromNode;
        }

        @Override
        public TileCoordinate getToNode() {
            return toNode;
        }
    }
}
