package com.moonlightpixels.jrpg.input

import com.badlogic.gdx.scenes.scene2d.Actor
import spock.lang.Specification

class ClickEventSpec extends Specification {
    void 'appliesTo returns true when click is inside actor'() {
        setup:
        Actor actor = new Actor(
            x: 0,
            y: 0,
            width: 100,
            height: 100
        )

        expect:
        new ClickEvent(50, 50).appliesTo(actor)
    }

    void 'appliesTo returns false when click is inside actor'() {
        setup:
        Actor actor = new Actor(
            x: 0,
            y: 0,
            width: 100,
            height: 100
        )

        expect:
        !new ClickEvent(150, 50).appliesTo(actor)
    }
}
