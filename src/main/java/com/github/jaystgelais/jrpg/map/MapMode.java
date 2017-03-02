package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.ActorSpriteSet;
import com.github.jaystgelais.jrpg.map.actor.SpriteSetData;

import java.util.Map;

public final class MapMode extends GameMode {
    private static final int DEFAULT_TIME_TO_TRAVERSE_TILE_MS = 300;

    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final String initialMapPath;
    private final TileCoordinate initialLocation;
    private final SpriteSetData heroSpriteSetData;
    private GameMap map;
    private Actor hero;

    public MapMode(
            final OrthographicCamera camera, final String mapPath,
            final TileCoordinate initialLocation, final SpriteSetData heroSpriteSetData) {
        this.camera = camera;
        this.initialLocation = initialLocation;
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        initialMapPath = mapPath;
        this.heroSpriteSetData = heroSpriteSetData;
    }

    @Override
    public String getKey() {
        return "mapMode";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        if (params.containsKey("map")) {
            loadMap((String) params.get("map"));
        } else {
            loadMap(initialMapPath);
            ActorSpriteSet heroSpriteSet = new ActorSpriteSet(
                    heroSpriteSetData, DEFAULT_TIME_TO_TRAVERSE_TILE_MS, assetManager
            );
            hero = new Actor(map, heroSpriteSet, initialLocation);
            map.setFocalPoint(hero);
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (inputService.isPressed(Inputs.CANCEL)) {
            getGame().exitGame();
        }
        hero.handleInput(inputService);
    }

    @Override
    public void update(final long elapsedTime) {
        hero.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        map.renderBackground(graphicsService);

        graphicsService.renderStart();
        hero.render(graphicsService);
        graphicsService.renderEnd();

        map.renderForeground(graphicsService);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    private void loadMap(final String mapPath) {
        if (!assetManager.isLoaded(mapPath, TiledMap.class)) {
            assetManager.load(mapPath, TiledMap.class);
            assetManager.finishLoading();
        }
        TiledMap tiledMap = assetManager.get(mapPath);
        TiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(
                tiledMap, getGame().getGraphicsService().getSpriteBatch()
        );
        this.map = new GameMap(camera, tiledMap, mapRenderer);
    }
}
