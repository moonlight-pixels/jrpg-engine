package com.moonlightpixels.jrpg.map.internal;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;
import com.moonlightpixels.jrpg.internal.inject.GraphicsModule;
import com.moonlightpixels.jrpg.map.JRPGMap;
import com.moonlightpixels.jrpg.map.TiledProps;

import javax.inject.Inject;
import java.util.SortedMap;
import java.util.TreeMap;

public final class DefaultJRPGMap implements JRPGMap {
    private final TiledMap tiledMap;
    private final TiledMapRenderer mapRenderer;
    private final SortedMap<Integer, JRPGMapLayer> mapLayers = new TreeMap<>();

    @Inject
    public DefaultJRPGMap(@Named(GraphicsModule.MAP) final GraphicsContext graphicsContext,
                          final AssetManager assetManager,
                          @Assisted final String mapPath) {
        this.tiledMap = assetManager.get(mapPath);
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, graphicsContext.getSpriteBatch());
        buildMapLayers(graphicsContext);
    }

    @Override
    public void update(final float delta) {
        mapLayers.keySet().forEach(mapLayerIndex -> {
            mapLayers.get(mapLayerIndex).update(delta);
        });
    }

    @Override
    public void render() {
        mapLayers.keySet().forEach(mapLayerIndex -> {
            mapLayers.get(mapLayerIndex).render();
        });
    }

    private void buildMapLayers(final GraphicsContext graphicsContext) {
        for (MapLayer mapLayer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledMapLayer = (TiledMapTileLayer) mapLayer;

            if (tiledMapLayer.getProperties().containsKey(TiledProps.Layer.MAP_LAYER)) {
                int mapLayerIndex = tiledMapLayer.getProperties().get(TiledProps.Layer.MAP_LAYER, Integer.class);

                JRPGMapLayer layer = mapLayers.computeIfAbsent(
                    mapLayerIndex,
                    i -> new JRPGMapLayer(i, mapRenderer, graphicsContext.createStage())
                );

                switch (tiledMapLayer.getProperties().get(TiledProps.Layer.LAYER_TYPE, String.class)) {
                    case TiledProps.Layer.MAP_LAYER_VALUE_BACKGROUND:
                        layer.addBackgroundLayer(tiledMapLayer);
                        break;
                    case TiledProps.Layer.MAP_LAYER_VALUE_FOREGROUND:
                        layer.addForegroundLayer(tiledMapLayer);
                        break;
                    case TiledProps.Layer.MAP_LAYER_VALUE_COLLISION:
                        layer.setCollisionLayer(tiledMapLayer);
                        break;
                    default:
                }
            }
        }
    }

    @Override
    public float getTileWidth() {
        return tiledMap.getProperties().get("tilewidth", Integer.class);
    }

    @Override
    public float getTileHeight() {
        return tiledMap.getProperties().get("tileheight", Integer.class);
    }
}
