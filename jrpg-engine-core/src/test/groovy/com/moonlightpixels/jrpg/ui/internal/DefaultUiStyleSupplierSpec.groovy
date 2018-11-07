package com.moonlightpixels.jrpg.ui.internal

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.moonlightpixels.jrpg.GdxSpecification
import com.moonlightpixels.jrpg.ui.Panel
import com.moonlightpixels.jrpg.ui.UiStyle

class DefaultUiStyleSupplierSpec extends GdxSpecification {
    void 'Supplied UiStyle has required defaults'() {
        when:
        UiStyle uiStyle = new DefaultUiStyleSupplier(new DefaultUiStyle(null)).get()

        then:
        uiStyle.get(BitmapFont)
        uiStyle.get(Panel.PanelStyle)
    }
}
