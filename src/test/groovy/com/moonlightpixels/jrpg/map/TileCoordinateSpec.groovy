package com.moonlightpixels.jrpg.map

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class TileCoordinateSpec extends Specification {
    void 'GetAbove'() {
        expect:
        new TileCoordinate(2, 2).above == new TileCoordinate(2, 3)
    }

    void 'GetBelow'() {
        expect:
        new TileCoordinate(2, 2).below == new TileCoordinate(2, 1)
    }

    void 'GetLeft'() {
        expect:
        new TileCoordinate(2, 2).left == new TileCoordinate(1, 2)
    }

    void 'GetRight'() {
        expect:
        new TileCoordinate(2, 2).right == new TileCoordinate(3, 2)
    }

    void 'Contract between equals() and hashcode()'() {
        expect:
        EqualsVerifier.forClass(TileCoordinate).verify()
    }
}
