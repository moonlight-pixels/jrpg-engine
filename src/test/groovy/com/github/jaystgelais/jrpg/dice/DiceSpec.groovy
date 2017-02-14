package com.github.jaystgelais.jrpg.dice

import spock.lang.Specification

/**
 * Created by jgelais on 12/27/16.
 */
class DiceSpec extends Specification {
    void 'roll correctly sums up rolls'() {
        when:
        Dice dice = new Dice()
        dice.add(new DieRoll(1, 1))
        dice.add(new FixedRoll(2))

        then:
        dice.roll() == 3
    }

    void 'correctly parses input'() {
        when:
        Dice dice = new Dice()
                .add('1d1')
                .add('2')

        then:
        dice.roll() == 3
    }

    void 'rejects invalid input'() {
        when:
        new Dice()
                .add('1d6d5')

        then:
        thrown IllegalArgumentException
    }
}
