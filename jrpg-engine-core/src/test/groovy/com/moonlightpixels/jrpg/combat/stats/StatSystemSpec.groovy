package com.moonlightpixels.jrpg.combat.stats

import spock.lang.Specification

class StatSystemSpec extends Specification {
    Stat maxHp
    Stat level

    void setup() {
        maxHp = BaseStat.builder()
            .key(RequiredStats.MaxHP)
            .name('Max HP')
            .shortName('HPM')
            .build()
        level = BaseStat.builder()
            .key(RequiredStats.Level)
            .name('Level')
            .shortName('LVL')
            .build()
    }

    void 'Can retrieve registered stats'() {
        when:
        StatSystem statSystem = StatSystem.builder().stat(maxHp).stat(level).build()

        then:
        statSystem.getStat(RequiredStats.MaxHP) == maxHp
        statSystem.getStat(RequiredStats.Level) == level
    }

    void 'Builder throws IllegalArgumentException if MaxHp stat not provided.'() {
        when:
        StatSystem.builder().stat(level).build()

        then:
        thrown IllegalArgumentException
    }

    void 'Builder throws IllegalArgumentException if level stat not provided.'() {
        when:
        StatSystem.builder().stat(maxHp).build()

        then:
        thrown IllegalArgumentException
    }

    void 'getStat throws IllegalArgumentException if stat doesnt exist'() {
        setup:
        StatSystem statSystem = StatSystem.builder().stat(maxHp).stat(level).build()

        when:
        statSystem.getStat(TestStats.STR)

        then:
        thrown IllegalArgumentException
    }

    private static enum TestStats implements Stat.Key {
        STR
    }
}
