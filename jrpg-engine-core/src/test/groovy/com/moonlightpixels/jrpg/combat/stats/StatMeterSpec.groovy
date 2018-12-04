package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

class StatMeterSpec extends Specification {
    StatHolder player
    Stat maxHP

    void setup() {
        player = Mock()
        maxHP = BaseStat.builder()
            .key(RequiredStats.MaxHP)
            .name('Max HP')
            .shortName('HPM')
            .cap(9999)
            .minValue(1)
            .build()
    }

    void 'meter value is normaized when max value chages'() {
        setup:
        List<StatModifier> modifiers = []
        player.getBaseValue(RequiredStats.MaxHP) >> 100
        player.getStatModifiers(RequiredStats.MaxHP) >> modifiers
        StatMeter hp = new StatMeter(player, maxHP, maxHP.getValue(player))

        when:
        modifiers << new StatAddition(RequiredStats.MaxHP, 50)
        hp.setValue(maxHP.getValue(player))

        then:
        hp.getValue() == 150

        when:
        modifiers.clear()

        then:
        hp.getValue() == 100
    }

    void 'addition does not push value out of bounds'(final int addition, final int expectedValue) {
        setup:
        player.getBaseValue(RequiredStats.MaxHP) >> 100
        player.getStatModifiers(RequiredStats.MaxHP) >> []
        StatMeter hp = new StatMeter(player, maxHP, 50)

        when:
        hp.add(addition)

        then:
        hp.getValue() == expectedValue

        where:
        addition | expectedValue
        25       | 75
        100      | 100
        -25      | 25
        -100     | 0
    }

    void 'multiplication does not push value out of bounds'(final float multiplier, final int expectedValue) {
        setup:
        player.getBaseValue(RequiredStats.MaxHP) >> 100
        player.getStatModifiers(RequiredStats.MaxHP) >> []
        StatMeter hp = new StatMeter(player, maxHP, 50)

        when:
        hp.mulitply(multiplier)

        then:
        hp.getValue() == expectedValue

        where:
        multiplier | expectedValue
        1.5f       | 75
        3.0        | 100
        -1.0       | 0
    }
}
