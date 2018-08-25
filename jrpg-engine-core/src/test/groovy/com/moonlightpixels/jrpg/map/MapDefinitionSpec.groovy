package com.moonlightpixels.jrpg.map

import spock.lang.Specification

class MapDefinitionSpec extends Specification {
    void 'Base class calls configure on child implementations'() {
        setup:
        JRPGMap jrpgMap = Mock(JRPGMap)
        JRPGMapFactory mapFactory = Mock(JRPGMapFactory)
        MapDefinition mapDefinition = Spy(new MapDefinition('pathtomap') {
            @Override
            protected void configure(final JRPGMap map) { }
        })

        when:
        mapDefinition.load(mapFactory)

        then:
        1 * mapFactory.create(_) >> jrpgMap
        1 * mapDefinition.configure(jrpgMap)
    }
}
