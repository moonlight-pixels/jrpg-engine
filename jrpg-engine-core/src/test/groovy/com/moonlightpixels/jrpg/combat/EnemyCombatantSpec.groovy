package com.moonlightpixels.jrpg.combat

import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.StatHolder
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import spock.lang.Specification

class EnemyCombatantSpec extends Specification {
    StatSystem statSystem
    CombatAI enemyAI
    Enemy enemy
    EnemyCombatant enemyCombatant

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
        enemyAI = Mock()
        enemy = Enemy.builder()
            .key(Mock(Enemy.Key))
            .name('Metal Slime')
            .combatAI(enemyAI)
            .stat(RequiredStats.CombatTurnInterval, 1)
            .stat(RequiredStats.MaxHP, 100)
            .build()
        enemyCombatant = new EnemyCombatant(statSystem, enemy)
    }

    void 'hitpoints are initailly set to MaxHP'() {
        expect:
        enemyCombatant.getHitPoints().value == enemyCombatant.getStatValue(RequiredStats.MaxHP)
    }

    void 'Holder type is enemy'() {
        expect:
        enemyCombatant.getHolderType() == StatHolder.Type.Enemy
    }
}
