package com.github.jaystgelais.jrpg.dice

import spock.lang.Specification

/**
 * Created by jgelais on 2/14/17.
 */
class FixedRollSpec extends Specification {
    void 'All rolls correctly return inputted value'() {
        expect:
        new FixedRoll(1).roll() == 1
        new FixedRoll(11).roll() == 11
        new FixedRoll(-1).roll() == -1
        new FixedRoll(-21).roll() == -21
        new FixedRoll(0).roll() == 0
    }

    void 'toString test'(int input, String result) {
        expect:
        new FixedRoll(input).toString() == result

        where:
        input | result
        1     | '1'
        2     | '2'
        3     | '3'
        -1    | '-1'
    }
}
