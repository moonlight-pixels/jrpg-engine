package com.moonlightpixels.jrpg.map

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class TileCoordinateSpec extends Specification {
    void 'Contract between equals() and hashcode()'() {
        expect:
        EqualsVerifier.forClass(TileCoordinate).verify()
    }

    void 'getAdjacent(UP) returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getAdjacent(Direction.UP) == new TileCoordinate(1, 2)
    }

    void 'getAdjacent(DOWN) returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getAdjacent(Direction.DOWN) == new TileCoordinate(1, 0)
    }

    void 'getAdjacent(RIGHT) returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getAdjacent(Direction.RIGHT) == new TileCoordinate(2, 1)
    }

    void 'getAdjacent(LEFT) returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1).getAdjacent(Direction.LEFT) == new TileCoordinate(0, 1)
    }

    void 'getAbove() returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1, 1).getAbove() == new TileCoordinate(1, 1, 2)
    }

    void 'getBelow() returns the correct TileCoordinate'() {
        expect:
        new TileCoordinate(1, 1, 1).getBelow() == new TileCoordinate(1, 1, 0)
    }
}
