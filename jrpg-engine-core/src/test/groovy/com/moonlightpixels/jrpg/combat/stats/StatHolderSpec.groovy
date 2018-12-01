package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

class StatHolderSpec extends Specification {
    Map<Stat.Key, Integer> statValues
    List<StatAdditon> additons
    List<StatMutiplier> mutipliers
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
            List<StatAdditon> getStatAdditions(final Stat.Key stat) {
                return additons
            }

            @Override
            List<StatMutiplier> getStatMultipliers(final Stat.Key stat) {
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
        additons << new StatAdditon(TestStats.STR, 2)
        mutipliers << new StatMutiplier(TestStats.STR, 1.5f)

        expect:
        stat.getValue(statHolder) == 18
    }

    private enum TestStats implements Stat.Key {
        STR
    }
}
