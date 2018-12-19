package com.moonlightpixels.jrpg.save.internal.mapper

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import com.moonlightpixels.jrpg.internal.inject.InjectionContext
import com.moonlightpixels.jrpg.internal.inject.TestModule
import com.moonlightpixels.jrpg.save.internal.GameStateDataProvider
import spock.lang.Specification

class SavedGameStateMapperSpec extends Specification {
    StatSystem statSystem
    GameStateDataProvider gameStateDataProvider
    SavedPlayerCharacterMapper savedPlayerCharacterMapper
    SavedPartyMapper savedPartyMapper
    SavedGameStateMapper mapper

    void setup() {
        InjectionContext.reset()
        InjectionContext.addModule(new TestModule())
        statSystem = new StatSystem()
        statSystem.addStat(BaseStat.builder()
            .key(RequiredStats.MaxHP)
            .name('Max HP')
            .shortName('HPM')
            .build()
        )
        statSystem.addStat(BaseStat.builder()
            .key(RequiredStats.Level)
            .name('Level')
            .shortName('LVL')
            .build()
        )
        statSystem.addStat(BaseStat.builder()
            .key(RequiredStats.CombatTurnInterval)
            .name('Combat Turn Interval')
            .shortName('CTI')
            .build()
        )
        gameStateDataProvider = new GameStateDataProvider()
        savedPlayerCharacterMapper = new SavedPlayerCharacterMapper(statSystem)
        savedPartyMapper = new SavedPartyMapper()
        mapper = new SavedGameStateMapper(
            savedPlayerCharacterMapper,
            savedPartyMapper
        )
    }

    void 'save/load produces equal game states'() {
        setup:
        GameState gameState = gameStateDataProvider.createGameState()

        expect:
        gameState == mapper.map(mapper.map(gameState))
    }
}
