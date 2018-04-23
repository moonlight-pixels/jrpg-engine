package com.moonlightpixels.jrpg.ui.internal

import com.moonlightpixels.jrpg.ui.UiStyle
import spock.lang.Specification

class DefaultUiStyleSpec extends Specification {
    private UiStyle uiStyle

    void setup() {
        uiStyle = new DefaultUiStyle()
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
}
