package com.moonlightpixels.jrpg.config.internal

import com.moonlightpixels.jrpg.config.JRPGConfiguration
import com.moonlightpixels.jrpg.config.LaunchConfig
import spock.lang.Specification

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
}
