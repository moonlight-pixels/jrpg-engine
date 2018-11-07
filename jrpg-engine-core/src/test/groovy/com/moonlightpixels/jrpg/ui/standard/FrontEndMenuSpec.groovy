package com.moonlightpixels.jrpg.ui.standard

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.moonlightpixels.jrpg.ui.Panel
import com.moonlightpixels.jrpg.ui.SelectList
import com.moonlightpixels.jrpg.ui.UiStyle
import com.moonlightpixels.jrpg.ui.UserInterface
import com.moonlightpixels.jrpg.ui.util.LabeledSelectListBuilder
import spock.lang.Specification

class FrontEndMenuSpec extends Specification {
    FrontEndMenu frontEndMenu
    UserInterface userInterface
    UiStyle uiStyle
    SelectList.Action newGameAction
    SelectList.Action exitGameAction

    void setup() {
        newGameAction = Mock(SelectList.Action)
        exitGameAction = Mock(SelectList.Action)
        frontEndMenu = FrontEndMenu.builder()
            .newGameAction(newGameAction)
            .exitGameAction(exitGameAction)
            .build()
        uiStyle = Mock(UiStyle)
        userInterface = Mock(UserInterface) {
            getUiStyle() >> uiStyle
        }
    }

    void 'setPadding sets all 4 padding values'() {
        when:
        frontEndMenu.setPadding(1f)

        then:
        frontEndMenu.getLeftPadding() == 1f
        frontEndMenu.getRightPadding() == 1f
        frontEndMenu.getTopPadding() == 1f
        frontEndMenu.getBottomPadding() == 1f
    }

    void 'Builds menu with correct actions'() {
        setup:
        uiStyle.get(LabeledSelectListBuilder.STYLE, SelectList.SelectListStyle) >> {
            new SelectList.SelectListStyle(Mock(Texture))
        }
        uiStyle.get(_, Panel.PanelStyle) >> new Panel.PanelStyle(Mock(Drawable))
        uiStyle.createLabel(*_) >> Mock(Label)

        when:
        frontEndMenu.getMenu(userInterface).open()

        then:
        noExceptionThrown()
    }
}
