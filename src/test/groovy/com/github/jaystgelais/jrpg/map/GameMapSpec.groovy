package com.github.jaystgelais.jrpg.map

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.github.jaystgelais.jrpg.map.actor.Actor
import com.github.jaystgelais.jrpg.map.actor.PlayerController
import spock.lang.Specification

class GameMapSpec extends Specification {
    private TiledMap mockMap
    private TiledMapRenderer mockMapRenderer

    void setup() {
        mockMapRenderer = Mock(TiledMapRenderer)
        mockMap = Mock(TiledMap)
    }

    void 'test camera focus'(int actorX, int actorY, float cameraX, float cameraY) {
        setup:
        MapProperties mapProperties = new MapProperties()
        mapProperties.put('height', 40)
        mapProperties.put('width', 40)
        mapProperties.put('tileheight', 16)
        mapProperties.put('tilewidth', 16)
        _ * mockMap.getProperties() >> mapProperties
        _ * mockMap.getLayers() >> []

        OrthographicCamera testCamera = new OrthographicCamera()
        testCamera.viewportWidth = 320
        testCamera.viewportHeight = 240
        GameMap gameMap = new GameMap(testCamera, mockMap, mockMapRenderer, new Location() {
            @Override
            String getDescription(TileCoordinate tileCoordinate) {
                return ''
            }
        })
        Actor testActor = new Actor(gameMap, null, new PlayerController(), new TileCoordinate(actorX, actorY))
        gameMap.setFocalPoint(testActor)

        when:
        testActor.update(1L)
        gameMap.focusCamera()

        then:
        testCamera.position.x == cameraX
        testCamera.position.y == cameraY
        testCamera.position.z == 0

        where:
        actorX | actorY | cameraX | cameraY
        10     | 10     | 168     | 160
        1      | 1      | 160     | 120
        39     | 39     | 480     | 520
    }
}
