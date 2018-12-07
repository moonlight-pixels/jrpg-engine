package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

import java.util.function.Function

class CompositeStatSpec extends Specification {
    StatHolder statHolder
    Stat strengthStat
    Stat speedStat
    Stat athleticismStat
    Function<Map<Stat.Key, Integer>, Integer> statFunction

    void setup() {
        statHolder = Mock(StatHolder) {
            getHolderType() >> StatHolder.Type.Player
        }
        strengthStat = BaseStat.builder()
            .key(TestStats.STR)
            .name('STR')
            .shortName('STR')
            .build()
        speedStat = BaseStat.builder()
            .key(TestStats.SPD)
            .name('Speed')
            .shortName('SPD')
            .build()
        statFunction = new Function<Map<Stat.Key, Integer>, Integer>() {
            @Override
            Integer apply(final Map<Stat.Key, Integer> statMap) {
                return statMap.get(TestStats.STR) + statMap.get(TestStats.SPD)
            }
        }
        athleticismStat = CompositeStat.builder()
            .key(TestStats.ATH)
            .name('Athleticism')
            .shortName('ATH')
            .input(strengthStat)
            .input(speedStat)
            .statFunction(statFunction)
            .build()
    }

    void 'baseValue correctly executes stat formula'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getBaseValue(TestStats.SPD) >> 12
        statHolder.getStatModifiers(TestStats.STR) >> []
        statHolder.getStatModifiers(TestStats.SPD) >> []
        statHolder.getStatModifiers(TestStats.ATH) >> []

        expect:
        athleticismStat.getBaseValue(statHolder) == 22
        athleticismStat.getBaseValue(statHolder) == athleticismStat.getValue(statHolder)
    }

    void 'getValue() correctly applies modifiers'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getBaseValue(TestStats.SPD) >> 12
        statHolder.getStatModifiers(TestStats.STR) >> []
        statHolder.getStatModifiers(TestStats.SPD) >> []
        statHolder.getStatModifiers(TestStats.ATH) >> [ new StatAddition(TestStats.ATH, 10) ]

        expect:
        athleticismStat.getValue(statHolder) == 32
    }

    void 'getValue() caps value when it would exceed it'() {
        setup:
        statHolder.getBaseValue(TestStats.STR) >> 10
        statHolder.getBaseValue(TestStats.SPD) >> 12
        statHolder.getStatModifiers(TestStats.STR) >> []
        statHolder.getStatModifiers(TestStats.SPD) >> []
        statHolder.getStatModifiers(TestStats.ATH) >> []
        Stat newStat = CompositeStat.builder()
            .key(TestStats.ATH)
            .name('Athleticism')
            .shortName('ATH')
            .input(strengthStat)
            .input(speedStat)
            .statFunction(statFunction)
            .cap(20)
            .build()

        expect:
        newStat.getValue(statHolder) == 20
    }

    void 'builder throws null pointer exception if key is omitted'() {
        when:
        CompositeStat.builder()
            .name('Athleticism')
            .shortName('ATH')
            .input(strengthStat)
            .input(speedStat)
            .statFunction(statFunction)
            .build()

        then:
        thrown NullPointerException
    }

    void 'builder throws null pointer exception if name is omitted'() {
        when:
        CompositeStat.builder()
            .key(TestStats.ATH)
            .shortName('ATH')
            .input(strengthStat)
            .input(speedStat)
            .statFunction(statFunction)
            .build()

        then:
        thrown NullPointerException
    }

    void 'builder throws null pointer exception if shortName is omitted'() {
        when:
        CompositeStat.builder()
            .key(TestStats.ATH)
            .name('Athleticism')
            .input(strengthStat)
            .input(speedStat)
            .statFunction(statFunction)
            .build()

        then:
        thrown NullPointerException
    }

    void 'builder throws null pointer exception if statFunction is omitted'() {
        when:
        CompositeStat.builder()
            .key(TestStats.ATH)
            .name('Athleticism')
            .shortName('ATH')
            .input(strengthStat)
            .input(speedStat)
            .build()

        then:
        thrown NullPointerException
    }

    private enum TestStats implements Stat.Key {
        STR, SPD, ATH
    }
}
