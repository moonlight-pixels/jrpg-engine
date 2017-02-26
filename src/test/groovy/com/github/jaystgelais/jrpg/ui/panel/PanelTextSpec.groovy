package com.github.jaystgelais.jrpg.ui.panel

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.github.jaystgelais.jrpg.graphics.GraphicsService
import com.github.jaystgelais.jrpg.input.InputService
import com.github.jaystgelais.jrpg.input.Inputs
import com.github.jaystgelais.jrpg.testutils.GdxSpecification

class PanelTextSpec extends GdxSpecification {

    void 'long text is broken up into pages'() {
        setup:
        String text = '''And so she went on, taking first one side and then the other, and making  quite a
conversation of it altogether; but after a few minutes she heard  a voice outside, and stopped to listen. 'Mary Ann!
Mary Ann!' said the voice. 'Fetch me my gloves this moment!'  Then came a little pattering of feet on the stairs.
Alice knew it was  the Rabbit coming to look for her, and she trembled till she shook the  house, quite forgetting
that she was now about a thousand times as large  as the Rabbit, and had no reason to be afraid of it. Presently
the Rabbit came up to the door, and tried to open it; but, as  the door opened inwards, and Alice's elbow was pressed
hard against it,  that attempt proved a failure. Alice heard it say to itself 'Then I'll  go round and get in at
the window.' 'THAT you won't' thought Alice, and, after waiting till she fancied  she heard the Rabbit just
under the window, she suddenly spread out her  hand, and made a snatch in the air.'''
        GraphicsService graphicsService = Mock(GraphicsService)
        BitmapFont font = Spy(BitmapFont) {
            _ * draw(*_) >> { }
        }
        InputService inputService = Mock(InputService) {
            _ * isPressed(Inputs.OK) >> true
        }
        Panel panel = new Panel(400, 200,  new PanelPalette())
        panel.data.setTransitionTimeMs(0)
        panel.setContent(new PanelText(font, text))

        when:
        panel.update(100)
        panel.render(graphicsService)
        panel.handleInput(inputService)
        panel.update(100)

        then:
        panel.active
    }

    void 'OK input closes a panel with no additional pages'() {
        setup:
        GraphicsService graphicsService = Mock(GraphicsService)
        BitmapFont font = Spy(BitmapFont) {
            _ * draw(*_) >> { }
        }
        String text = 'Hello'
        InputService inputService = Mock(InputService) {
            _ * isPressed(Inputs.OK) >> true
        }
        Panel panel = new Panel(400, 200,  new PanelPalette())
        panel.data.setTransitionTimeMs(0)
        panel.setContent(new PanelText(font, text))

        when:
        panel.update(100)
        panel.render(graphicsService)
        panel.handleInput(inputService)
        panel.update(100)

        then:
        !panel.active
    }
}
