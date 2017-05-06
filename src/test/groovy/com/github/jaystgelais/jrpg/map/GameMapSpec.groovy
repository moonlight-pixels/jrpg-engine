package com.github.jaystgelais.jrpg.map

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.github.jaystgelais.jrpg.map.actor.Actor
import spock.lang.Specification

class GameMapSpec extends Specification {
    private OrthographicCamera mockCamera
    private TiledMap mockMap
    private TiledMapRenderer mockMapRenderer

    void setup() {
        mockCamera = Mock(OrthographicCamera)
        mockMapRenderer = Mock(TiledMapRenderer)
        mockMap = Mock(TiledMap)
    }

    void 'test camera focus'() {
        setup:
        MapProperties mapProperties = new MapProperties()
        mapProperties.put('height', 40)
        mapProperties.put('width', 40)
        mapProperties.put('tileheight', 16)
        mapProperties.put('tilewidth', 16)
        _ * mockMap.getProperties() >> mapProperties

        OrthographicCamera testCamera = new OrthographicCamera()
        GameMap gameMap = new GameMap(testCamera, mockMap, mockMapRenderer)
        Actor testActor = new Actor(gameMap, null, new TileCoordinate(10, 10))
        gameMap.setFocalPoint(testActor)

        when:
        testActor.update(1L)
        gameMap.focusCamera()

        then:
        testCamera.position.x == 160
        testCamera.position.y == 160
        testCamera.position.z == 0
    }

    void 'Non-null cells in Collision Detecion Layer trigger collision'() {
        setup:
        _ * mockMap.getLayers() >> Mock(MapLayers) {
            _ * get(GameMap.COLLISION_LAYER_NAME) >> Mock(TiledMapTileLayer) {
                _ * getCell(1, 1) >> Mock(TiledMapTileLayer.Cell)
            }
        }

        when:
        GameMap gameMap = new GameMap(mockCamera, mockMap, mockMapRenderer)

        then:
        gameMap.isCollision(new TileCoordinate(1, 1))
        !gameMap.isCollision(new TileCoordinate(1, 2))
    }
}
