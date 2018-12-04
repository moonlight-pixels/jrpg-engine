package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.player.PlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedPlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;

import java.util.Collections;

public final class SavedPlayerCharacterMapper {

    public SavedPlayerCharacter map(final PlayerCharacter playerCharacter) {
        SavedPlayerCharacter savedPlayerCharacter = new SavedPlayerCharacter();
        savedPlayerCharacter.setKey(playerCharacter.getKey());
        savedPlayerCharacter.setName(playerCharacter.getName());
        savedPlayerCharacter.setAnimationSetKey(playerCharacter.getAnimationSet());

        return savedPlayerCharacter;
    }

    public PlayerCharacter map(final SavedPlayerCharacter savedPlayerCharacter) throws SavedStateLoadExcpetion {
        PlayerCharacter playerCharacter = PlayerCharacter.builder()
            .key(savedPlayerCharacter.getKey())
            .name(savedPlayerCharacter.getName())
            .animationSet(savedPlayerCharacter.getAnimationSetKey())
            .equipmentSlots(Collections.emptyList())
            .build();

        return playerCharacter;
    }
}
