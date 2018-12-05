package com.moonlightpixels.jrpg.map.internal

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.map.TileCoordinate
import spock.lang.Specification

class JRPGMapLayerSpec extends Specification {
    private GraphicsContext graphicsContext
    private TiledMapRenderer mapRenderer
    private Stage stage
    private JRPGMapLayer mapLayer

    void setup() {
        graphicsContext = Mock(GraphicsContext) {
            getSpriteBatch() >> Mock(SpriteBatch) {
                getColor() >> Color.BLACK
            }
        }
        mapRenderer = Mock(TiledMapRenderer)
        stage = Mock(Stage)
        mapLayer = new JRPGMapLayer(0, this.mapRenderer, this.stage)
    }

    void 'render() draws components in order'() {
        setup:
        TiledMapTileLayer backgroundLayer = Mock(TiledMapTileLayer)
        TiledMapTileLayer collisionLayer = Mock(TiledMapTileLayer)
        TiledMapTileLayer foregroundLayer = Mock(TiledMapTileLayer)
        mapLayer.addBackgroundLayer(backgroundLayer)
        mapLayer.addForegroundLayer(foregroundLayer)
        mapLayer.setCollisionLayer(collisionLayer)

        when:
        mapLayer.render(graphicsContext)

        then:
        1 * mapRenderer.renderTileLayer(backgroundLayer)
        0 * mapRenderer.renderTileLayer(collisionLayer)

        then:
        1 * stage.draw()
        0 * mapRenderer.renderTileLayer(collisionLayer)

        then:
        1 * mapRenderer.renderTileLayer(foregroundLayer)
        0 * mapRenderer.renderTileLayer(collisionLayer)
    }

    void 'update() sorts actors by y position'() {
        setup:
        Group root = new Group()
        Actor actor1 = new Actor(y: 100)
        Actor actor2 = new Actor(y: 0)

        when:
        mapLayer.addActor(actor1)
        mapLayer.addActor(actor2)
        mapLayer.update(0.016f)

        then:
        _ * stage.getRoot() >> root
        _ * stage.addActor(_) >> { args ->
            root.addActor(args[0] as Actor)
        }
        _ * stage.getActors() >> root.children
        actor2.getZIndex() > actor1.getZIndex()
    }

    void 'removeActor() removes actor from stage'() {
        setup:
        Group root = Mock()
        stage.getRoot() >> root
        Actor actor = new Actor()

        when:
        mapLayer.removeActor(actor)

        then:
        1 * root.removeActor(actor)
    }

    void 'isOpen() return true if there is no tile in collision layer, false otherwise'() {
        setup:
        TiledMapTileLayer collisionLayer = Mock(TiledMapTileLayer) {
            getCell(1, 1) >> Mock(TiledMapTileLayer.Cell)
        }
        mapLayer.setCollisionLayer(collisionLayer)

        expect:
        mapLayer.isOpen(new TileCoordinate(0, 0))
        !mapLayer.isOpen(new TileCoordinate(1, 1))
    }
}
