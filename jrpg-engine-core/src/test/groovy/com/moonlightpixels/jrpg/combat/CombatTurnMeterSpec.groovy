package com.moonlightpixels.jrpg.combat

import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.StatHolder
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import spock.lang.Specification

class CombatTurnMeterSpec extends Specification {
    StatSystem statSystem
    StatHolder statHolder
    CombatTurnMeter meter

    void setup() {
        statSystem = new StatSystem()
        statSystem.addStat(
            BaseStat.builder()
                .key(RequiredStats.CombatTurnInterval)
                .name('combat turn interval')
                .shortName('cti')
                .build()
        )
        statHolder = Mock(StatHolder) {
            getBaseValue(RequiredStats.CombatTurnInterval) >> 2
            getStatModifiers(RequiredStats.CombatTurnInterval) >> []
        }
        meter = new CombatTurnMeter(statSystem, statHolder)
    }

    void 'meter starts at 0'() {
        expect:
        meter.percentFull == 0.0f
    }

    void 'meter fills up after a number fo ticks = to CombatTurnInterval'() {
        when:
        meter.tick()

        then:
        !meter.isFull()

        when:
        meter.tick()

        then:
        meter.isFull()
    }

    void 'reset() sets the meter back to 0'() {
        when:
        meter.tick()
        meter.tick()

        then:
        meter.isFull()

        when:
        meter.reset()

        then:
        meter.percentFull == 0.0f
    }
}
