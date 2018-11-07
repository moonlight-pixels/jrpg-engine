package com.moonlightpixels.jrpg.ui.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.moonlightpixels.jrpg.ui.SelectList
import com.moonlightpixels.jrpg.ui.UiStyle
import spock.lang.Specification

class LabeledSelectListBuilderSpec extends Specification {
    UiStyle uiStyle

    void setup() {
        uiStyle = Mock(UiStyle) {
            get(LabeledSelectListBuilder.STYLE, SelectList.SelectListStyle) >> {
                new SelectList.SelectListStyle(Mock(Texture))
            }
        }
    }

    void 'Builds SelectList based on inputs'() {
        setup:
        uiStyle.createLabel('item', _) >> Mock(Label)

        when:
        SelectList selectList = new LabeledSelectListBuilder(uiStyle)
            .addItem('item', null)
            .setColumns(2)
            .build()

        then:
        selectList.items.size == 1
        selectList.columns == 2
    }
}
