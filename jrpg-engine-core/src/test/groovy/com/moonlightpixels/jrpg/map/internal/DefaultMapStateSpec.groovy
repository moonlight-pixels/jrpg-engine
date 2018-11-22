package com.moonlightpixels.jrpg.map.internal

import com.badlogic.gdx.Graphics
import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.internal.GameStateHolder
import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.JRPGMapFactory
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import spock.lang.Specification

class DefaultMapStateSpec extends Specification {
    GraphicsContext graphicsContext

    private Graphics graphics
    GdxFacade gdx
    MapRegistry mapRegistry
    JRPGMapFactory mapFactory
    GameState gameState
    GameStateHolder gameStateHolder
    JRPG jrpg
    MapState mapState

    void setup() {
        graphicsContext = Mock(GraphicsContext)
        graphics = Mock(Graphics)
        gdx = Mock(GdxFacade) {
            getGraphics() >> graphics
        }
        mapRegistry = new MapRegistry()
        mapFactory = Mock(JRPGMapFactory)
        gameState = Mock(GameState) {
            isValid() >> true
        }
        GameStateHolder gameStateHolder = new GameStateHolder()
        gameStateHolder.setGameState(gameState)
        jrpg = Mock(JRPG)
        mapState = new DefaultMapState(graphicsContext, gdx, mapRegistry, mapFactory, gameStateHolder)
    }

    void 'update() updates and renders map'() {
        setup:
        JRPGMap map = Mock(JRPGMap)
        mapRegistry.register(new MyMapDefinition())
        mapFactory.create(_) >> map
        graphics.getDeltaTime() >> 0.1f
        gameState.getLocation() >> new Location(Maps.MYMAP, new TileCoordinate(0, 0))

        when:
        mapState.enter(jrpg)
        mapState.update(jrpg)

        then:
        1 * map.update(0.1f)

        then:
        1 * graphicsContext.activate()

        then:
        1 * map.render()
    }

    static enum Maps implements MapDefinition.Key {
        MYMAP
    }

    static class MyMapDefinition extends MapDefinition {
        MyMapDefinition() {
            super(Maps.MYMAP, '')
        }

        @Override
        protected void configure(final JRPGMap map) { }
    }
}
