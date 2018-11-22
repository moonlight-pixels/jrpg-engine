package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.map.character.internal.CharacterAnimationSetRegistry;
import com.moonlightpixels.jrpg.player.PlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedPlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;

import javax.inject.Inject;

public final class SavedPlayerCharacterMapper {
    private final CharacterAnimationSetRegistry characterAnimationSetRegistry;

    @Inject
    public SavedPlayerCharacterMapper(final CharacterAnimationSetRegistry characterAnimationSetRegistry) {
        this.characterAnimationSetRegistry = characterAnimationSetRegistry;
    }

    public SavedPlayerCharacter map(final PlayerCharacter playerCharacter) {
        SavedPlayerCharacter savedPlayerCharacter = new SavedPlayerCharacter();
        savedPlayerCharacter.setKey(playerCharacter.getKey());
        savedPlayerCharacter.setName(playerCharacter.getName());
        savedPlayerCharacter.setAnimationSetKey(playerCharacter.getAnimationSet().getKey());

        return savedPlayerCharacter;
    }

    public PlayerCharacter map(final SavedPlayerCharacter savedPlayerCharacter) throws SavedStateLoadExcpetion {
        PlayerCharacter playerCharacter = PlayerCharacter.builder()
            .key(savedPlayerCharacter.getKey())
            .name(savedPlayerCharacter.getName())
            .animationSet(
                characterAnimationSetRegistry.getCharacterAnimationSet(savedPlayerCharacter.getAnimationSetKey())
            )
            .build();

        return playerCharacter;
    }
}
