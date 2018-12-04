package com.moonlightpixels.jrpg.save.internal

import com.moonlightpixels.jrpg.GameState
import com.moonlightpixels.jrpg.internal.DefaultGameState
import com.moonlightpixels.jrpg.map.Location
import com.moonlightpixels.jrpg.map.MapDefinition
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet
import com.moonlightpixels.jrpg.player.Cast
import com.moonlightpixels.jrpg.player.Party
import com.moonlightpixels.jrpg.player.PlayerCharacter
import com.moonlightpixels.jrpg.save.internal.mapper.SavedGameStateMapper
import com.moonlightpixels.jrpg.save.internal.mapper.SavedPartyMapper
import com.moonlightpixels.jrpg.save.internal.mapper.SavedPlayerCharacterMapper

class GameStateDataProvider {
    final SavedPlayerCharacterMapper savedPlayerCharacterMapper
    final SavedPartyMapper savedPartyMapper
    final SavedGameStateMapper mapper

    GameStateDataProvider() {
        savedPlayerCharacterMapper = new SavedPlayerCharacterMapper()
        savedPartyMapper = new SavedPartyMapper()
        mapper = new SavedGameStateMapper(
            savedPlayerCharacterMapper,
            savedPartyMapper
        )
    }

    GameState createGameState() {
        GameState gameState = new DefaultGameState()
        gameState.setDefaultSaveId('mySaveId')
        Players.values().each { gameState.cast.addToRoster(createPlayerCharacter(it)) }
        gameState.cast.configureParties(
            createParty(gameState.cast, Players.TERRA, Players.SABIN, Players.EDGAR, Players.GOGO),
            createParty(gameState.cast, Players.CELES, Players.LOCKE, Players.GAU, Players.SETZER),
            createParty(gameState.cast, Players.MOG, Players.SHADOW, Players.RELM, Players.STRAGO)
        )

        return gameState
    }

    SavedGameState createSavedGameState() {
        return mapper.map(createGameState())
    }

    private static PlayerCharacter createPlayerCharacter(final Players key) {
        PlayerCharacter playerCharacter = PlayerCharacter.builder()
            .key(key)
            .name(key.toString())
            .animationSet(new AnimationKey(id: key.toString()))
            .equipmentSlots([])
            .build()

        return playerCharacter
    }

    private static Party createParty(final Cast cast, final Players ...players) {
        Party party = new Party(
            1,
            players.size(),
            new Location(Maps.NARSHE_EXTERIOR, new TileCoordinate(1, 1))
        )
        players.each {
            cast.getPlayerCharacter(it).ifPresent { party.addMember(it) }
        }

        return party
    }

    static enum Players implements PlayerCharacter.Key {
        TERRA,
        LOCKE,
        EDGAR,
        SABIN,
        CELES,
        SHADOW,
        CYAN,
        GAU,
        SETZER,
        MOG,
        STRAGO,
        RELM,
        UMARO,
        GOGO
    }

    static enum Maps implements MapDefinition.Key {
        NARSHE_EXTERIOR,
        NARSHE_CAVES
    }

    static class AnimationKey implements CharacterAnimationSet.Key {
        String id

        boolean equals(final Object o) {
            return (o instanceof AnimationKey) ? id == ((AnimationKey) o).id : false
        }

        int hashCode() {
            return (id != null ? id.hashCode() : 0)
        }
    }
}
