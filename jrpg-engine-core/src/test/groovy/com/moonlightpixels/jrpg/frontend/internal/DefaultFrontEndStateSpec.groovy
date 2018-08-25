package com.moonlightpixels.jrpg.frontend.internal

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.moonlightpixels.jrpg.frontend.FrontEndConfig
import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.ui.UserInterface
import spock.lang.Specification

class DefaultFrontEndStateSpec extends Specification {
    private FrontEndConfig frontEndConfig
    private UserInterface userInterface
    private GraphicsContext graphicsContext
    private SpriteBatch spriteBatch
    private AssetManager assetManager
    private DefaultFrontEndState frontEndState
    private JRPG jrpg

    void setup() {
        frontEndConfig = Mock(FrontEndConfig)
        userInterface = Mock(UserInterface)
        spriteBatch = Mock(SpriteBatch)
        graphicsContext = Mock(GraphicsContext)  {
            getSpriteBatch() >> spriteBatch
        }
        assetManager = Mock(AssetManager)
        frontEndState = new DefaultFrontEndState(frontEndConfig, userInterface, graphicsContext, assetManager)
        jrpg = Mock(JRPG)
    }

    void 'update() updates the UserInterface'() {
        setup:
        frontEndConfig.getTitleScreenPath() >> Optional.empty()

        when:
        frontEndState.update(jrpg)

        then:
        1 * userInterface.update()
    }
}
