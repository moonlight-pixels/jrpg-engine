package com.moonlightpixels.jrpg.ui.internal

import com.badlogic.gdx.Graphics
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext
import com.moonlightpixels.jrpg.ui.UiStyle
import com.moonlightpixels.jrpg.ui.UserInterface
import spock.lang.Specification

class DefaultUserInterfaceSpec extends Specification {
    private GraphicsContext graphicsContext
    private Stage stage
    private Group rootGroup
    private GdxFacade gdx
    private Graphics graphics
    private UiStyle uiStyle

    void setup() {
        stage = Mock()
        graphicsContext = Mock(GraphicsContext) {
            createStage() >> stage
        }
        rootGroup = Mock()
        graphics = Mock()
        gdx = Mock(GdxFacade) {
            getGraphics() >> graphics
        }
        uiStyle = Mock()
    }

    void 'clear() calls clear on Stage'() {
        when:
        new DefaultUserInterface(graphicsContext, gdx, uiStyle).clear()

        then:
        1 * stage.clear()
    }

    void 'update() updates and draws the stage, using the stage camera'() {
        when:
        new DefaultUserInterface(graphicsContext, gdx, uiStyle).update()

        then:
        1 * graphics.getDeltaTime() >> 0.1f
        1 * stage.act(0.1f)
        1 * stage.draw()
    }

    void 'remove() removes only the targetted actor'() {
        setup:
        Actor actor1 = Mock(Actor)
        Actor actor2 = Mock(Actor)
        Actor actor3 = Mock(Actor)
        UserInterface userInterface = new DefaultUserInterface(graphicsContext, gdx, uiStyle)
        userInterface.add(actor1)
        userInterface.add(actor2)
        userInterface.add(actor3)

        when:
        userInterface.remove(actor2)

        then:
        _ * stage.getRoot() >> rootGroup
        1 * rootGroup.removeActor(actor2)
    }
}
