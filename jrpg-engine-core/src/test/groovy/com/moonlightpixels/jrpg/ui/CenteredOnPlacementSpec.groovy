package com.moonlightpixels.jrpg.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import spock.lang.Specification

class CenteredOnPlacementSpec extends Specification {
    void 'correctly centers the given actor'() {
        setup:
        ActorPlacement placement = new CenteredOnPlacement(100f, 100f)
        Actor actor = Mock(Actor) {
            getWidth() >> 100f
            getHeight() >> 50f
        }

        expect:
        placement.getX(actor) == 50
        placement.getY(actor) == 75
    }
}
