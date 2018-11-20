package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.save.internal.SavedPlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;
import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry;
import com.moonlightpixels.jrpg.player.PlayerCharacter;

import javax.inject.Inject;

public final class SavedPlayerCharacterMapper {
    private final KeyLoader<PlayerCharacter.Key> playerCharacterKeyLoader;
    private final CharacterAnimationSetRegistry characterAnimationSetRegistry;

    @Inject
    public SavedPlayerCharacterMapper(final KeyLoader<PlayerCharacter.Key> playerCharacterKeyLoader,
                                      final CharacterAnimationSetRegistry characterAnimationSetRegistry) {
        this.playerCharacterKeyLoader = playerCharacterKeyLoader;
        this.characterAnimationSetRegistry = characterAnimationSetRegistry;
    }

    public SavedPlayerCharacter map(final PlayerCharacter playerCharacter) {
        SavedPlayerCharacter savedPlayerCharacter = new SavedPlayerCharacter();
        savedPlayerCharacter.setKey(playerCharacter.getKey().toString());
        savedPlayerCharacter.setName(playerCharacter.getName());
        savedPlayerCharacter.setAnimationSetId(playerCharacter.getAnimationSet().getId());

        return savedPlayerCharacter;
    }

    public PlayerCharacter map(final SavedPlayerCharacter savedPlayerCharacter) throws SavedStateLoadExcpetion {
        PlayerCharacter playerCharacter = new PlayerCharacter(
            playerCharacterKeyLoader.load(savedPlayerCharacter.getKey())
        );
        playerCharacter.setName(savedPlayerCharacter.getName());
        playerCharacter.setAnimationSet(
            characterAnimationSetRegistry.getCharacterAnimationSet(savedPlayerCharacter.getAnimationSetId())
        );


        return playerCharacter;
    }
}
