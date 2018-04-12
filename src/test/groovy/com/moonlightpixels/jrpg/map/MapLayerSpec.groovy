package com.moonlightpixels.jrpg.map

import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import spock.lang.Specification

class MapLayerSpec extends Specification {

    void 'Non-null cells in Collision Detecion Layer trigger collision'() {
        setup:
        TiledMapTileLayer collisionLayer = Mock(TiledMapTileLayer) {
            _ * getCell(1, 1) >> Mock(TiledMapTileLayer.Cell)
        }

        when:
        MapLayer mapLayer = new MapLayer(0, Mock(TiledMapRenderer))
        mapLayer.setCollisionLayer(collisionLayer)

        then:
        mapLayer.isCollision(new TileCoordinate(1, 1, 0))
        !mapLayer.isCollision(new TileCoordinate(1, 2, 0))
    }
}
