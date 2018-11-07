package com.moonlightpixels.jrpg.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import spock.lang.Specification

class PanelSpec extends Specification {
    void 'Panel draws background'() {
        setup:
        Batch batch = Mock(Batch)
        Drawable background = Mock(Drawable)

        when:
        new Panel(new Panel.PanelStyle(background)).draw(batch, 1.0f)

        then:
        1 * background.draw(batch, *_)
    }

    void 'Panel draws content'() {
        setup:
        Batch batch = Mock(Batch)
        Drawable background = Mock(Drawable)
        Actor content = Mock(Actor) {
            isVisible() >> true
        }

        when:
        new Panel(content, new Panel.PanelStyle(background)).draw(batch, 1.0f)

        then:
        1 * content.draw(batch, *_)
    }

    void 'Panel will size to child when width/height are unset'() {
        setup:
        Drawable background = Mock(Drawable)
        Actor content = Mock(Actor) {
            getWidth() >> 100f
            getHeight() >> 50f
        }

        when:
        Panel panel = new Panel(content, new Panel.PanelStyle(background))

        then:
        panel.getWidth() == 100f
        panel.getHeight() == 50f
    }

    void 'Panel will NOT size to child when width/height are set'() {
        setup:
        Drawable background = Mock(Drawable)
        Actor content = Mock(Actor) {
            getWidth() >> 100f
            getHeight() >> 50f
        }

        when:
        Panel panel = new Panel(content, new Panel.PanelStyle(background))
        panel.setWidth(50f)
        panel.setHeight(25f)

        then:
        panel.getWidth() == 50
        panel.getHeight() == 25
    }

    void 'A panel with no content will not fail to calculate width/height'() {
        setup:
        Drawable background = Mock(Drawable)

        when:
        Panel panel = new Panel(new Panel.PanelStyle(background))

        then:
        panel.getWidth() == 0f
        panel.getHeight() == 0f
    }

    void 'A Panel will update its position if a placement is set'() {
        setup:
        ActorPlacement placement = Mock(ActorPlacement) {
            getX(_ as Actor) >> 10f
            getY(_ as Actor) >> 20f
        }
        Drawable background = Mock(Drawable)
        Panel panel = new Panel(new Panel.PanelStyle(background))
        panel.setX(100f)
        panel.setY(100f)

        when:
        panel.setPlacement(placement)
        panel.act(0.0125f)

        then:
        panel.getX() == 10f
        panel.getY() == 20f
    }

    void 'PanelStyle copy constructor copies background'() {
        setup:
        Drawable background = Mock(Drawable)
        Panel.PanelStyle source = new Panel.PanelStyle(background)

        when:
        Panel.PanelStyle copy = new Panel.PanelStyle(source)

        then:
        copy.background == source.background
    }
}
