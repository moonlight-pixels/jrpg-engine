package com.moonlightpixels.jrpg.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.moonlightpixels.jrpg.testutils.GdxSpecification
import com.moonlightpixels.jrpg.graphics.factory.SpriteBatchFactory

class GraphicsServiceImplSpec extends GdxSpecification {
    void 'Manages the spritebatch state'() {
        setup:
        SpriteBatch spriteBatch = Mock(SpriteBatch)
        SpriteBatchFactory spriteBatchFactory = Mock(SpriteBatchFactory) {
            _ * createSpriteBatch() >> spriteBatch
        }
        Pixmap pixmap = Mock(Pixmap)
        GraphicsService graphicsService = new GraphicsServiceImpl(new AssetManager(), spriteBatchFactory)

        when:
        graphicsService.init()
        graphicsService.renderStart()
        graphicsService.drawSprite(pixmap, 0, 0)
        graphicsService.renderEnd()
        graphicsService.dispose()

        then:
        1 * spriteBatch.begin()
        1 * spriteBatch.draw(_, 0, 0)
        1 * spriteBatch.end()
        1 * spriteBatch.dispose()
    }

    void 'clears the screen'() {
        setup:
        SpriteBatch spriteBatch = Mock(SpriteBatch)
        SpriteBatchFactory spriteBatchFactory = Mock(SpriteBatchFactory) {
            _ * createSpriteBatch() >> spriteBatch
        }
        GraphicsService graphicsService = new GraphicsServiceImpl(new AssetManager(), spriteBatchFactory)

        when:
        graphicsService.clearScreen()

        then:
        1 * Gdx.gl20.glClear(_)
    }
}
