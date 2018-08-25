package com.moonlightpixels.jrpg.map.internal

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.moonlightpixels.jrpg.GdxSpecification
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.TiledProps

class DefaultJRPGMapSpec extends GdxSpecification {

    void 'Correctly parses map file'() {
        setup:
        GraphicsContext graphicsContext = Mock(GraphicsContext)
        AssetManager assetManager = Mock(AssetManager)

        when:
        JRPGMap map = new DefaultJRPGMap(graphicsContext, assetManager, 'path/to/map.tmx')

        then:
        noExceptionThrown()
        1 * assetManager.get('path/to/map.tmx') >> getTestMap()
        map.tileHeight == 16
        map.tileWidth == 16
    }

    private TiledMap getTestMap() {
        TiledMap tiledMap = new TiledMap()
        tiledMap.properties.put('tilewidth', 16)
        tiledMap.properties.put('tileheight', 16)
        tiledMap.layers.add(createMapLayer(0, TiledProps.Layer.MAP_LAYER_VALUE_BACKGROUND))
        tiledMap.layers.add(createMapLayer(0, TiledProps.Layer.MAP_LAYER_VALUE_COLLISION))
        tiledMap.layers.add(createMapLayer(0, TiledProps.Layer.MAP_LAYER_VALUE_FOREGROUND))
        tiledMap.layers.add(createMapLayer(1, TiledProps.Layer.MAP_LAYER_VALUE_BACKGROUND))
        tiledMap.layers.add(createMapLayer(1, TiledProps.Layer.MAP_LAYER_VALUE_COLLISION))
        tiledMap.layers.add(createMapLayer(1, TiledProps.Layer.MAP_LAYER_VALUE_FOREGROUND))

        return tiledMap
    }

    private MapLayer createMapLayer(int index, String type) {
        MapLayer mapLayer = new TiledMapTileLayer(100, 100, 16, 16)
        mapLayer.properties.put(TiledProps.Layer.MAP_LAYER, index)
        mapLayer.properties.put(TiledProps.Layer.LAYER_TYPE, type)

        return mapLayer
    }
}
