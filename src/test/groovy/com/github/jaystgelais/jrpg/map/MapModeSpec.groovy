package com.github.jaystgelais.jrpg.map

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.github.jaystgelais.jrpg.Game
import com.github.jaystgelais.jrpg.graphics.GraphicsService
import com.github.jaystgelais.jrpg.input.InputService
import com.github.jaystgelais.jrpg.map.actor.SpriteSetData
import com.github.jaystgelais.jrpg.testutils.GdxSpecification

class MapModeSpec extends GdxSpecification {
    public static final String TEST_MAP_PATH = '/test/map/path'
    public static final String TEST_SPRITESHEET_PATH = '/test/spritesheet/path'

    void 'Updates, renders map and actors'() {
        setup:
        TiledMap mockMap = Mock(TiledMap) {
            MapProperties mapProperties = new MapProperties()
            mapProperties.put('height', 40)
            mapProperties.put('width', 40)
            mapProperties.put('tileheight', 16)
            mapProperties.put('tilewidth', 16)
            _ * getProperties() >> mapProperties
        }
        TiledMapRenderer mockMapRenderer = Mock(TiledMapRenderer)
        OrthographicCamera testCamera = new OrthographicCamera()
        testCamera.viewportWidth = 320
        testCamera.viewportHeight = 240
        SpriteSetData testSpriteSetData = new SpriteSetData(TEST_SPRITESHEET_PATH, 4, 4)
        AssetManager mockAssetManager = Mock(AssetManager) {
            _ * isLoaded(*_) >> true
            _ * get(TEST_MAP_PATH) >> mockMap
            _ * get(TEST_SPRITESHEET_PATH, Texture) >> Mock(Texture) {
                _ * getWidth() >> 16
                _ * getHeight() >> 16
            }
        }
        GraphicsService mockGraphicsService = Mock(GraphicsService) {
            _ * getTileMapRenderer(_) >> mockMapRenderer
        }

        when:
        MapMode mapMode = new MapMode(testCamera, TEST_MAP_PATH, new TileCoordinate(1, 1),
                testSpriteSetData, mockAssetManager)
        new Game([mapMode] as Set, mapMode.key, mockGraphicsService, Mock(InputService))
        mapMode.onEnter([:])
        mapMode.update(1L)
        mapMode.render(mockGraphicsService)

        then:
        1 * mockMapRenderer.render(GameMap.BACKGROUND_LAYERS)
        1 * mockMapRenderer.render(GameMap.FOREGROUND_LAYERS)
    }
}
