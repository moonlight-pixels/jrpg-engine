package com.github.jaystgelais.jrpg.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;

public abstract class MapDefinition {

    public abstract GameMap getMap(final GraphicsService graphicsService, final AssetManager assetManager);

    protected final GameMap loadMap(final GraphicsService graphicsService, final AssetManager assetManager,
                                    final String mapPath, final Location parentLocation) {
        if (!assetManager.isLoaded(mapPath, TiledMap.class)) {
            assetManager.load(mapPath, TiledMap.class);
            assetManager.finishLoading();
        }
        TiledMap tiledMap = assetManager.get(mapPath);
        TiledMapRenderer mapRenderer = graphicsService.getTileMapRenderer(tiledMap);
        return new GameMap(graphicsService.getCamera(), tiledMap, mapRenderer, parentLocation);
    }
}
