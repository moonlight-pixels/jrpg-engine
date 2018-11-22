package com.moonlightpixels.jrpg.save.internal.mapper

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.save.internal.GameStateDataProvider
import spock.lang.Specification

class SavedGameStateMapperSpec extends Specification {
    GameStateDataProvider gameStateDataProvider
    SavedPlayerCharacterMapper savedPlayerCharacterMapper
    SavedPartyMapper savedPartyMapper
    SavedGameStateMapper mapper

    void setup() {
        gameStateDataProvider = new GameStateDataProvider()
        savedPlayerCharacterMapper = new SavedPlayerCharacterMapper()
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
