package com.moonlightpixels.jrpg.random

import spock.lang.Specification

class OddmentTableSpec extends Specification {
    void 'table with a single row returns expected value'() {
        setup:
        OddmentTable table = OddmentTable.builder(String).row(1, 'result').build()

        expect:
        table.getValue() == 'result'
    }
}
