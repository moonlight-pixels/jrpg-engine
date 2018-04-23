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
        Actor content = Mock(Actor)

        when:
        new Panel(content, new Panel.PanelStyle(background)).draw(batch, 1.0f)

        then:
        _ * content.isVisible() >> true
        1 * content.draw(batch, *_)
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
