package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.internal.DefaultGameState;
import com.moonlightpixels.jrpg.player.Cast;
import com.moonlightpixels.jrpg.player.internal.DefaultPlayerCharacter;
import com.moonlightpixels.jrpg.player.Party;
import com.moonlightpixels.jrpg.player.PlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedGameState;
import com.moonlightpixels.jrpg.save.internal.SavedPlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SavedGameStateMapper {
    private final SavedPlayerCharacterMapper savedPlayerCharacterMapper;
    private final SavedPartyMapper savedPartyMapper;

    @Inject
    public SavedGameStateMapper(final SavedPlayerCharacterMapper savedPlayerCharacterMapper,
                                final SavedPartyMapper savedPartyMapper) {
        this.savedPlayerCharacterMapper = savedPlayerCharacterMapper;
        this.savedPartyMapper = savedPartyMapper;
    }

    public SavedGameState map(final GameState gameState) {
        SavedGameState savedGameState = new SavedGameState();
        savedGameState.setSaveId(gameState.getDefaultSaveId().orElse(null));
        savedGameState.setCast(
            gameState.getCast().getRoster().stream()
                .map(savedPlayerCharacterMapper::map)
                .collect(Collectors.toList())
        );
        savedGameState.setRoster(
            gameState.getCast().getRoster().stream()
                .map(PlayerCharacter::getKey)
                .collect(Collectors.toList())
        );
        savedGameState.setParties(
            gameState.getCast().getCurrentParties().stream()
                .map(savedPartyMapper::map)
                .collect(Collectors.toList())
        );
        savedGameState.setActivePartyIndex(
            gameState.getCast().getCurrentParties().indexOf(
                gameState.getCast().getActiveParty()
            )
        );

        return savedGameState;
    }

    public GameState map(final SavedGameState savedGameState) throws SavedStateLoadExcpetion {
        GameState gameState = new DefaultGameState();
        gameState.setDefaultSaveId(savedGameState.getSaveId());
        mapCastFromSavedData(savedGameState, gameState.getCast());

        return gameState;
    }

    private void mapCastFromSavedData(final SavedGameState savedGameState,
                                      final Cast cast) throws SavedStateLoadExcpetion {
        Map<DefaultPlayerCharacter.Key, PlayerCharacter> playerCharacterMap = new HashMap<>();
        for (SavedPlayerCharacter savedPlayerCharacter : savedGameState.getCast()) {
            final PlayerCharacter playerCharacter = savedPlayerCharacterMapper.map(savedPlayerCharacter);
            playerCharacterMap.put(savedPlayerCharacter.getKey(), playerCharacter);
            cast.addToCast(playerCharacter);
        }
        for (DefaultPlayerCharacter.Key key : savedGameState.getRoster()) {
            cast.addToRoster(key);
        }
        List<Party> parties = savedGameState.getParties().stream()
            .map(savedParty -> savedPartyMapper.map(savedParty, playerCharacterMap))
            .collect(Collectors.toList());
        Party activeParty = parties.remove(savedGameState.getActivePartyIndex());
        cast.configureParties(activeParty, parties.toArray(new Party[0]));
    }
}
