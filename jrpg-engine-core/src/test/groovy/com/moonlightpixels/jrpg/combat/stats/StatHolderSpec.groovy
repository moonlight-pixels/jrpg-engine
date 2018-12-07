package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

class StatHolderSpec extends Specification {
    Map<Stat.Key, Integer> statValues
    List<StatAddition> additons
    List<StatMultiplier> mutipliers
    StatHolder statHolder
    Stat stat

    void setup() {
        statValues = [:]
        additons = []
        mutipliers = []
        statHolder = new StatHolder() {
            @Override
            int getBaseValue(final Stat.Key key) {
                return statValues[key]
            }

            @Override
            StatHolder.Type getHolderType() {
                return null
            }

            @Override
            List<StatAddition> getStatAdditions(final Stat.Key stat) {
                return additons
            }

            @Override
            List<StatMultiplier> getStatMultipliers(final Stat.Key stat) {
                return mutipliers
            }
        }
        stat = BaseStat.builder()
            .key(TestStats.STR)
            .name('STR')
            .shortName('STR')
            .build()
    }

    void 'stat modifiers are applied in correct order'() {
        setup:
        statValues[TestStats.STR] = 10
        additons << new StatAddition(TestStats.STR, 2)
        mutipliers << new StatMultiplier(TestStats.STR, 1.5f)

        expect:
        stat.getValue(statHolder) == 18
    }

    private enum TestStats implements Stat.Key {
        STR
    }
}
