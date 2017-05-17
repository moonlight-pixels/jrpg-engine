package com.github.jaystgelais.jrpg.ui.panel

import com.badlogic.gdx.graphics.Pixmap
import com.github.jaystgelais.jrpg.graphics.GraphicsService
import com.github.jaystgelais.jrpg.testutils.GdxSpecification

class PanelSpec extends GdxSpecification {

    void 'Panel constructs a properly sized sprite'() {
        given:
        GraphicsService graphicsService = Mock(GraphicsService)
        Panel panel = new Panel(new PanelData(100, 200).setTransitionTimeMs(0))

        when:
        panel.render(graphicsService)

        then:
        1 * graphicsService.drawSprite(_ as Pixmap, 0, 0) >> { arguments ->
            Pixmap pixmap = arguments[0]
            assert pixmap.width == 100
            assert pixmap.height == 200
        }
    }
}
