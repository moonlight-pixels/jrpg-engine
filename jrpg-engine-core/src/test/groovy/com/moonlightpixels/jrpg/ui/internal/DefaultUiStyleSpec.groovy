package com.moonlightpixels.jrpg.ui.internal

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.moonlightpixels.jrpg.internal.gdx.factory.LabelFactory
import com.moonlightpixels.jrpg.ui.UiStyle
import spock.lang.Specification

class DefaultUiStyleSpec extends Specification {
    private LabelFactory labelFactory
    private UiStyle uiStyle

    void setup() {
        labelFactory = Mock()
        uiStyle = new DefaultUiStyle(labelFactory)
        uiStyle.set(UiStyle.DEFAULT_STYLE, 'default')
        uiStyle.set('fancy', 'fancy')
    }

    void 'get() returns default style when no style is given.'() {
        expect:
        uiStyle.get(String) == 'default'
    }

    void 'get() returns requested style when.'() {
        expect:
        uiStyle.get('fancy', String) == 'fancy'
    }

    void 'get() returns default style when requested style does not exist.'() {
        expect:
        uiStyle.get('extra-fancy', String) == 'default'
    }

    void 'createLabel() delegates to factory'() {
        setup:
        Label.LabelStyle mystyle = new Label.LabelStyle(Mock(BitmapFont), Mock(Color))
        uiStyle.set('mystyle', mystyle)

        when:
        uiStyle.createLabel('mytext', 'mystyle')

        then:
        1 * labelFactory.create('mytext', mystyle)
    }
}
