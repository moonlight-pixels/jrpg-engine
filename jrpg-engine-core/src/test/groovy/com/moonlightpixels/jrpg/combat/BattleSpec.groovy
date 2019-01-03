package com.moonlightpixels.jrpg.combat

import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.player.Party
import spock.lang.Specification

class BattleSpec extends Specification {
    private static final float TIME_PER_TICK = 0.01f

    Telegraph telegraph
    CombatConfig combatConfig
    MessageDispatcher messageDispatcher
    StatSystem statSystem
    Party party
    Enemy enemy
    CombatAI enemyAI
    Battle battle

    void setup() {
        telegraph = Mock()
        MessageDispatcher messageDispatcher = new MessageDispatcher()
        messageDispatcher.addListener(
            telegraph,
            CombatMessageTypes.START_ANIMATION
        )
        messageDispatcher.addListener(
            telegraph,
            CombatMessageTypes.START_BATTLE
        )

        combatConfig = Mock(CombatConfig) {
            getTimePerTick() >> TIME_PER_TICK
        }
        statSystem = new StatSystem()
        statSystem.addStat(
            BaseStat.builder()
                .key(RequiredStats.CombatTurnInterval)
                .name('combat turn interval')
                .shortName('cti')
                .build()
        )
        party = new Party(1, 2, new Location(Mock(MapDefinition.Key), new TileCoordinate(0, 0)))
        enemyAI = Mock()
        enemy = Enemy.builder()
            .key(Mock(Enemy.Key))
            .name('Metal Slime')
            .combatAI(enemyAI)
            .stat(RequiredStats.CombatTurnInterval, 1)
            .build()
        battle = new Battle(
            combatConfig,
            messageDispatcher,
            statSystem,
            party,
            new Encounter(Arrays.asList(enemy))
        )
    }

    void 'Battle sends START_BATTLE message on init'() {
        when:
        battle.update(TIME_PER_TICK)

        then:
        1 * telegraph.handleMessage(*_) >> { arguments ->
            Telegram msg = arguments[0] as Telegram
            assert msg.message == CombatMessageTypes.START_BATTLE
            return true
        }
    }
}
