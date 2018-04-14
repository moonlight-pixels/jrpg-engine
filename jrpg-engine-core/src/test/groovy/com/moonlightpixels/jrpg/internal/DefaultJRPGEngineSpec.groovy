package com.moonlightpixels.jrpg.internal

import com.moonlightpixels.jrpg.JRPGEngine
import com.moonlightpixels.jrpg.JRPGSpec
import spock.lang.Specification

import java.util.function.Consumer

class DefaultJRPGEngineSpec extends Specification {
    void 'run() invokes config builder'() {
        setup:
        Consumer<JRPGSpec> definition = Mock(Consumer)

        when:
        JRPGEngine.run(definition)

        then:
        1 * definition.accept(_)
    }
}
