package com.moonlightpixels.jrpg.combat

import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.StatMeter
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import com.moonlightpixels.jrpg.player.PlayerCharacter
import spock.lang.Specification

class PlayerCombatantSpec extends Specification {
    StatSystem statSystem
    PlayerCharacter playerCharacter
    PlayerCombatant playerCombatant

    void setup() {
        statSystem = new StatSystem()
        statSystem.addStat(
            BaseStat.builder()
                .key(RequiredStats.CombatTurnInterval)
                .name('combat turn interval')
                .shortName('cti')
                .build()
        )
        statSystem.addStat(
            BaseStat.builder()
                .key(RequiredStats.MaxHP)
                .name('Max HP')
                .shortName('HP')
                .build()
        )
        playerCharacter = Mock()
        playerCombatant = new PlayerCombatant(statSystem, playerCharacter)
    }

    void 'getHitPoints() delegates to the player'() {
        setup:
        StatMeter hippoints = new StatMeter(playerCharacter, statSystem.getStat(RequiredStats.MaxHP), 100)
        playerCharacter.getHitPoints() >> hippoints
        playerCharacter.getBaseValue(RequiredStats.MaxHP) >> 150
        playerCharacter.getStatModifiers(RequiredStats.MaxHP) >> []

        expect:
        playerCombatant.getHitPoints().value == 100
    }

    void 'getMeter() delegates to the player'() {
        when:
        playerCombatant.getMeter(RequiredStats.MaxHP)

        then:
        1 * playerCharacter.getMeter(RequiredStats.MaxHP)
    }

    void 'getStatValue() delegates to the player'() {
        when:
        playerCombatant.getStatValue(RequiredStats.MaxHP)

        then:
        1 * playerCharacter.getStatValue(RequiredStats.MaxHP)
    }

    void 'combat turn meter uses players CombatTurnInterval stat'() {
        setup:
        playerCharacter.getBaseValue(RequiredStats.CombatTurnInterval) >> 1
        playerCharacter.getStatModifiers(RequiredStats.CombatTurnInterval) >> []

        expect:
        !playerCombatant.combatTurnMeter.full

        when:
        playerCombatant.combatTurnMeter.tick()

        then:
        playerCombatant.combatTurnMeter.full
    }
}
