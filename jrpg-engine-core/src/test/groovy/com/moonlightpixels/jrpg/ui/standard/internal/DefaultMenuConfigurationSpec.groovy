package com.moonlightpixels.jrpg.ui.standard.internal

import com.moonlightpixels.jrpg.ui.SelectList
import com.moonlightpixels.jrpg.ui.standard.FrontEndMenu
import spock.lang.Specification

import java.util.function.Consumer

class DefaultMenuConfigurationSpec extends Specification {
    private DefaultMenuConfiguration defaultMenuConfiguration

    void setup() {
        defaultMenuConfiguration = new DefaultMenuConfiguration()
    }

    void 'calls all frontend menu consumers'() {
        setup:
        Consumer<FrontEndMenu> frontendMenuConsumer1 = Mock()
        Consumer<FrontEndMenu> frontendMenuConsumer2 = Mock()
        FrontEndMenu frontEndMenu = FrontEndMenu.builder()
            .exitGameAction(Mock(SelectList.Action))
            .newGameAction(Mock(SelectList.Action))
            .build()

        when:
        defaultMenuConfiguration.frontend(frontendMenuConsumer1)
        defaultMenuConfiguration.frontend(frontendMenuConsumer2)
        defaultMenuConfiguration.configureFrontEndMenu(frontEndMenu)

        then:
        1 * frontendMenuConsumer1.accept(frontEndMenu)
        1 * frontendMenuConsumer2.accept(frontEndMenu)
    }
}
