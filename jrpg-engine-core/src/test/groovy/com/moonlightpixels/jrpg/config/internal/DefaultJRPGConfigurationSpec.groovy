package com.moonlightpixels.jrpg.config.internal

import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import com.moonlightpixels.jrpg.ui.UiStyle
import spock.lang.Specification

import java.util.function.Consumer

class DefaultJRPGConfigurationSpec extends Specification {
    void 'validate passes if LaunchConfig is set'() {
        expect:
        JRPGConfiguration configuration = new DefaultJRPGConfiguration()
        configuration.setLaunchConfig(LaunchConfig.builder()
            .resolutionWidth(640)
            .resolutionHeight(480)
            .fullscreen(true)
            .build())
        configuration.validate()
    }

    void 'validate throws IllegalStateException if LaunchConfig is NOT set'() {
        when:
        new DefaultJRPGConfiguration().validate()

        then:
        thrown IllegalStateException
    }

    void 'configure(UiStyle) can handle not having a configuration consumer'() {
        when:
        new DefaultJRPGConfiguration().configure(null as UiStyle)

        then:
        noExceptionThrown()
    }

    void 'configure(UiStyle) calls configuration consumer when set'() {
        setup:
        UiStyle uiStyle = Mock(UiStyle)
        Consumer<UiStyle> consumer = Mock(Consumer)

        when:
        JRPGConfiguration jrpgConfiguration = new DefaultJRPGConfiguration()
        jrpgConfiguration.uiStyle(consumer)
        jrpgConfiguration.configure(uiStyle)

        then:
        1 * consumer.accept(uiStyle)
    }
}
