package com.moonlightpixels.jrpg.map.internal

import com.badlogic.gdx.Graphics
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.animation.AnimationSetProvider
import com.moonlightpixels.jrpg.internal.GameStateHolder
import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.JRPGMapFactory
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.map.character.CharacterAnimation
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet
import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry
import com.moonlightpixels.jrpg.map.character.internal.PlayerInputCharacterController
import spock.lang.Specification

class DefaultMapStateSpec extends Specification {
    GraphicsContext graphicsContext

    Graphics graphics
    GdxFacade gdx
    JRPGMap map
    MapRegistry mapRegistry
    CharacterAnimationSetRegistry characterAnimationSetRegistry
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
        map = Mock(JRPGMap) {
            getTileCoordinateXY(_) >> new Vector2(10, 10)
        }
        mapRegistry = new MapRegistry()
        characterAnimationSetRegistry = new CharacterAnimationSetRegistry(Mock(AssetManager))
        CharacterAnimationSet.Key animationKey = Mock()
        CharacterAnimationSet characterAnimationSet = new CharacterAnimationSet(animationKey, 10, 10, 2)
        characterAnimationSet.addAnimationFrames(
            CharacterAnimation.StandDown,
            [ Mock(TextureRegion) ].toArray(new TextureRegion[0])
        )
        characterAnimationSetRegistry.register(animationKey, new AnimationSetProvider<CharacterAnimationSet>() {
            @Override
            CharacterAnimationSet get(final AssetManager assetManager) {
                return characterAnimationSet
            }
        })
        mapFactory = Mock(JRPGMapFactory) {
            create(_) >> map
        }
        gameState = Mock(GameState) {
            isValid() >> true
            getHeroAnimationSet() >> animationKey
        }
        gameStateHolder = new GameStateHolder()
        gameStateHolder.setGameState(gameState)
        jrpg = Mock(JRPG)
        mapState = new DefaultMapState(
            graphicsContext,
            gdx,
            mapRegistry,
            characterAnimationSetRegistry,
            mapFactory,
            gameStateHolder,
            new PlayerInputCharacterController(), menu
        )
    }

    void 'update() updates and renders map'() {
        setup:
        mapRegistry.register(new MyMapDefinition())
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
