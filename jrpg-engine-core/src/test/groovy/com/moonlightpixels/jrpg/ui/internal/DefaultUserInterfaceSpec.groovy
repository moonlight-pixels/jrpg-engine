package com.moonlightpixels.jrpg.ui.internal

import com.badlogic.gdx.Graphics
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade
import com.moonlightpixels.jrpg.ui.UserInterface
import spock.lang.Specification

class DefaultUserInterfaceSpec extends Specification {
    private Camera camera
    private Stage stage
    private Group rootGroup
    private GdxFacade gdx
    private Graphics graphics
    private SpriteBatch spriteBatch

    void setup() {
        camera = Mock(Camera)
        stage = Mock(Stage)
        rootGroup = Mock(Group)
        graphics = Mock(Graphics)
        gdx = Mock(GdxFacade) {
            _ * getGraphics() >> graphics
        }
        spriteBatch = Mock(SpriteBatch)
    }

    void 'clear() calls clear on Stage'() {
        when:
        new DefaultUserInterface(camera, stage, gdx, spriteBatch).clear()

        then:
        1 * stage.clear()
    }

    void 'update() updates and draws the stage, using the stage camera'() {
        when:
        new DefaultUserInterface(camera, stage, gdx, spriteBatch).update()

        then:
        1 * graphics.getDeltaTime() >> 0.1f
        1 * stage.act(0.1f)
        1 * spriteBatch.setProjectionMatrix(camera.projection)
        1 * stage.draw()
    }

    void 'remove() removes only the targetted actor'() {
        setup:
        Actor actor1 = Mock(Actor)
        Actor actor2 = Mock(Actor)
        Actor actor3 = Mock(Actor)
        UserInterface userInterface = new DefaultUserInterface(camera, stage, gdx, spriteBatch)
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
