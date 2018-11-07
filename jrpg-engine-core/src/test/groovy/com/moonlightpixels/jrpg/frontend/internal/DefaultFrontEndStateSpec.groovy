package com.moonlightpixels.jrpg.frontend.internal

import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.moonlightpixels.jrpg.frontend.FrontEndConfig
import com.moonlightpixels.jrpg.input.ClickEvent
import com.moonlightpixels.jrpg.input.ControlEvent
import com.moonlightpixels.jrpg.input.InputScheme
import com.moonlightpixels.jrpg.internal.JRPG
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.ui.InputHandler
import com.moonlightpixels.jrpg.ui.Menu
import com.moonlightpixels.jrpg.ui.MenuState
import com.moonlightpixels.jrpg.ui.UserInterface
import spock.lang.Specification

import java.util.function.Consumer

class DefaultFrontEndStateSpec extends Specification {
    private FrontEndConfig frontEndConfig
    private UserInterface userInterface
    private GraphicsContext graphicsContext
    private SpriteBatch spriteBatch
    private AssetManager assetManager
    private Menu menu
    private DefaultFrontEndState frontEndState
    private JRPG jrpg
    private MenuState menuState
    private InputHandler inputHandler
    private Consumer<Menu> menuOnClose

    void setup() {
        frontEndConfig = Mock(FrontEndConfig)
        userInterface = Mock(UserInterface)
        spriteBatch = Mock(SpriteBatch)
        graphicsContext = Mock(GraphicsContext)  {
            getSpriteBatch() >> spriteBatch
        }
        assetManager = Mock(AssetManager)
        menuState = Mock(MenuState) {
            inputHandler = Mock(InputHandler)
            getInputHandler() >> inputHandler
        }
        menuOnClose = Mock()
        menu = new Menu(
            userInterface,
            menuState,
            menuOnClose
        )
        frontEndState = new DefaultFrontEndState(frontEndConfig, userInterface, graphicsContext, assetManager, menu)
        jrpg = Mock(JRPG)
    }

    void 'enter() opens menu'() {
        setup:
        frontEndConfig.getTitleScreenPath() >> Optional.empty()

        when:
        frontEndState.enter(jrpg)

        then:
        1 * menuState.enter(_)
    }

    void 'enter() loads title screen if configured'() {
        setup:
        frontEndConfig.getTitleScreenPath() >> Optional.of('path')

        when:
        frontEndState.enter(jrpg)

        then:
        1 * assetManager.get('path', Texture)
    }

    void 'update() updates the UserInterface'() {
        setup:
        frontEndConfig.getTitleScreenPath() >> Optional.empty()

        when:
        frontEndState.update(jrpg)

        then:
        1 * userInterface.update()
    }

    void 'update() draws title screen if present'() {
        setup:
        Texture titleScreenTexture = Mock()
        frontEndConfig.getTitleScreenPath() >> Optional.of('path')
        assetManager.get('path', Texture) >> titleScreenTexture

        when:
        frontEndState.enter(jrpg)
        frontEndState.update(jrpg)

        then:
        1 * spriteBatch.draw(titleScreenTexture, 0, 0)
    }

    void 'exit() closes menu'() {
        when:
        frontEndState.exit(jrpg)

        then:
        1 * menuOnClose.accept(_)
    }

    void 'control events are routed to menus input handler if state is active'() {
        setup:
        frontEndConfig.getTitleScreenPath() >> Optional.of('path')

        when:
        frontEndState.enter(jrpg)
        frontEndState.handleControlEvent(ControlEvent.ACTION_PRESSED)

        then:
        1 * inputHandler.handleControlEvent(ControlEvent.ACTION_PRESSED)
    }

    void 'click events are routed to menus input handler if state is active'() {
        setup:
        ClickEvent clickEvent = new ClickEvent(1, 1)
        frontEndConfig.getTitleScreenPath() >> Optional.of('path')

        when:
        frontEndState.enter(jrpg)
        frontEndState.handleClickEvent(clickEvent)

        then:
        1 * inputHandler.handleClickEvent(clickEvent)
    }

    void 'input scheme settings propagate to menus input handler'() {
        when:
        frontEndState.setInputScheme(InputScheme.Controller)

        then:
        inputHandler.setInputScheme(InputScheme.Controller)
    }

    void 'onMessage() does not handle message'() {
        expect:
        !frontEndState.onMessage(jrpg, Mock(Telegram))
    }
}
