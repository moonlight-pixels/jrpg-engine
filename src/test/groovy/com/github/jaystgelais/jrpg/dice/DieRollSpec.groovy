package com.github.jaystgelais.jrpg.dice

import spock.lang.Specification

/**
 * Created by jgelais on 12/27/16.
 */
class DieRollSpec extends Specification {
    void 'All rolls correctly bounded by defined die'(int rolls, int sides, int min, int max) {
        when:
        int result = new DieRoll(rolls, sides).roll()

        then:
        result >= min
        result <= max

        where:
        rolls | sides | min | max
        1     | 6     | 1   | 6
        2     | 6     | 2   | 12
        3     | 4     | 3   | 12
        2     | 8     | 2   | 16
        1     | 1     | 1   | 1
    }

    void 'toString test'(int rolls, int sides, String result) {
        expect:
        new DieRoll(rolls, sides).toString() == result

        where:
        rolls | sides | result
        1     | 6     | '1d6'
        2     | 6     | '2d6'
        3     | 4     | '3d4'
        2     | 8     | '2d8'
        1     | 1     | '1d1'
    }
}
