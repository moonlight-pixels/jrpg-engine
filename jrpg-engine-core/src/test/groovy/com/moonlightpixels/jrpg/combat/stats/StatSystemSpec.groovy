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
        StatSystem statSystem = new StatSystem()
        statSystem.addStat(maxHp)
        statSystem.addStat(level)

        then:
        statSystem.isValid()
        statSystem.getStat(RequiredStats.MaxHP) == maxHp
        statSystem.getStat(RequiredStats.Level) == level
    }

    void 'StatSystem is not valid if MaxHp stat not provided.'() {
        when:
        StatSystem statSystem = new StatSystem()
        statSystem.addStat(level)

        then:
        !statSystem.isValid()
    }

    void 'StatSystem is not valid  if level stat not provided.'() {
        when:
        StatSystem statSystem = new StatSystem()
        statSystem.addStat(maxHp)

        then:
        !statSystem.isValid()
    }

    void 'getStat throws IllegalArgumentException if stat doesnt exist'() {
        setup:
        StatSystem statSystem = new StatSystem()
        statSystem.addStat(maxHp)
        statSystem.addStat(level)

        when:
        statSystem.getStat(TestStats.STR)

        then:
        thrown IllegalArgumentException
    }

    private static enum TestStats implements Stat.Key {
        STR
    }
}
