package com.moonlightpixels.jrpg.map

import spock.lang.Specification

class MapDefinitionSpec extends Specification {
    void 'Base class calls configure on child implementations'() {
        setup:
        JRPGMap jrpgMap = Mock(JRPGMap)
        JRPGMapFactory mapFactory = Mock(JRPGMapFactory)
        MapDefinition mapDefinition = Spy(TestMapDefinition)

        when:
        mapDefinition.load(mapFactory)

        then:
        1 * mapFactory.create(_) >> jrpgMap
        1 * mapDefinition.configure(jrpgMap)
    }

    static class TestMapDefinition extends MapDefinition {
        TestMapDefinition() {
            super(MapKeys.THE_MAP, 'mapPath')
        }

        @Override
        protected void configure(final JRPGMap map) { }
    }

    static enum MapKeys implements MapDefinition.Key { THE_MAP }
}
