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

import java.util.Map;

public final class MapMode extends GameMode {
    public static final int MOVEMENT_RATE_PX = 8;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final String initialMapPath;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;

    public MapMode(final OrthographicCamera camera, final String mapPath) {
        this.camera = camera;
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        initialMapPath = mapPath;
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
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (inputService.isPressed(Inputs.CANCEL)) {
            getGame().exitGame();
        } else if (inputService.isPressed(Inputs.UP)) {
            camera.translate(0, MOVEMENT_RATE_PX);
        } else if (inputService.isPressed(Inputs.DOWN)) {
            camera.translate(0, -MOVEMENT_RATE_PX);
        } else if (inputService.isPressed(Inputs.LEFT)) {
            camera.translate(-MOVEMENT_RATE_PX, 0);
        } else if (inputService.isPressed(Inputs.RIGHT)) {
            camera.translate(MOVEMENT_RATE_PX, 0);
        }
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        assetManager.dispose();
    }

    private void loadMap(final String mapPath) {
        if (!assetManager.isLoaded(mapPath, TiledMap.class)) {
            assetManager.load(mapPath, TiledMap.class);
            assetManager.finishLoading();
        }
        map = assetManager.get(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(camera.viewportWidth / 2, getMapHeight() - (camera.viewportHeight / 2), 0);
    }

    private int getMapHeight() {
        return map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
    }
}
