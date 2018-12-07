package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

class BaseStatSpec extends Specification {
    StatHolder statHolder

    void setup() {
        statHolder = Mock(StatHolder) {
            getHolderType() >> StatHolder.Type.Player
        }
    }

    void 'value equals base value when no modifiers are applied'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getStatModifiers(TestStats.STR) >> []
        Stat stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('STR')
            .shortName('STR')
            .build()

        expect:
        stat.getBaseValue(statHolder) == stat.getValue(statHolder)
    }

    void 'getValue() correctly applies modifiers'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getStatModifiers(TestStats.STR) >> [ new StatAddition(TestStats.STR, 10) ]
        Stat stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('STR')
            .shortName('STR')
            .build()

        expect:
        stat.getBaseValue(statHolder) == 10
        stat.getValue(statHolder) == 20
    }

    void 'getValue() caps value when it would exceed it'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getStatModifiers(TestStats.STR) >> [ new StatAddition(TestStats.STR, 10) ]
        Stat stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('STR')
            .shortName('STR')
            .cap(15)
            .build()

        expect:
        stat.getBaseValue(statHolder) == 10
        stat.getValue(statHolder) == 15
    }

    void 'builder throws null pointer exception if key is omitted'() {
        when:
        BaseStat.builder()
            .name('STR')
            .shortName('STR')
            .build()

        then:
        thrown NullPointerException
    }

    void 'builder throws null pointer exception if name is omitted'() {
        when:
        BaseStat.builder()
            .key(TestStats.STR)
            .shortName('STR')
            .build()

        then:
        thrown NullPointerException
    }

    void 'builder throws null pointer exception if shortName is omitted'() {
        when:
        BaseStat.builder()
            .key(TestStats.STR)
            .name('STR')
            .build()

        then:
        thrown NullPointerException
    }

    private enum TestStats implements Stat.Key {
        STR
    }
}
