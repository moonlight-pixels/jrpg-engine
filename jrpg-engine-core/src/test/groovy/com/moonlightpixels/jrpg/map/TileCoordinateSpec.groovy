package com.moonlightpixels.jrpg.map

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class TileCoordinateSpec extends Specification {
    void 'Contract between equals() and hashcode()'() {
        expect:
        EqualsVerifier.forClass(TileCoordinate).verify()
    }

    void 'getAbove() returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getAbove() == new TileCoordinate(1, 2)
    }

    void 'getBelow() returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getBelow() == new TileCoordinate(1, 0)
    }

    void 'getRight() returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getRight() == new TileCoordinate(2, 1)
    }

    void 'getLeft() returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getLeft() == new TileCoordinate(0, 1)
    }
}
