package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

import java.util.function.Function

class BaseStatSpec extends Specification {
    StatHolder statHolder

    void setup() {
        statHolder = Mock()
    }

    void 'value equals base value when no modifiers are applied'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getStatModifiers(TestStats.STR) >> []
        Stat stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('Strength')
            .shortName('STR')
            .build()

        expect:
        stat.getBaseValue(statHolder) == stat.getValue(statHolder)
    }

    void 'getValue() correctly applies modifiers'() {
        setup:
        Function<Integer, Integer> modifier = new Function<Integer, Integer>() {
            @Override
            Integer apply(final Integer a) {
                return a + 10
            }
        }
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getStatModifiers(TestStats.STR) >> [ modifier ]
        Stat stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('Strength')
            .shortName('STR')
            .build()

        expect:
        stat.getBaseValue(statHolder) == 10
        stat.getValue(statHolder) == 20
    }

    void 'getValue() caps value when it would exceed it'() {
        setup:
        Function<Integer, Integer> modifier = new Function<Integer, Integer>() {
            @Override
            Integer apply(final Integer a) {
                return a + 10
            }
        }
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getStatModifiers(TestStats.STR) >> [ modifier ]
        Stat stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('Strength')
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
            .name('Strength')
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
            .name('Strength')
            .build()

        then:
        thrown NullPointerException
    }

    private enum TestStats implements Stat.Key {
        STR
    }
}
