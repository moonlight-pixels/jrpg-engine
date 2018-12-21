package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.player.internal.DefaultPlayerCharacter;
import com.moonlightpixels.jrpg.player.PlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedPlayerCharacter;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;

import javax.inject.Inject;

public final class SavedPlayerCharacterMapper {
    private final StatSystem statSystem;

    @Inject
    public SavedPlayerCharacterMapper(final StatSystem statSystem) {
        this.statSystem = statSystem;
    }

    public SavedPlayerCharacter map(final PlayerCharacter playerCharacter) {
        SavedPlayerCharacter savedPlayerCharacter = new SavedPlayerCharacter();
        savedPlayerCharacter.setKey(playerCharacter.getKey());
        savedPlayerCharacter.setName(playerCharacter.getName());
        savedPlayerCharacter.setAnimationSetKey(playerCharacter.getAnimationSet());
        statSystem.getStats().stream().map(Stat::getKey).forEach(stat -> {
            savedPlayerCharacter.getStats().add(
                new SavedPlayerCharacter.StatValue(stat, playerCharacter.getBaseValue(stat))
            );
        });

        return savedPlayerCharacter;
    }

    public PlayerCharacter map(final SavedPlayerCharacter savedPlayerCharacter) throws SavedStateLoadExcpetion {
        DefaultPlayerCharacter.DefaultPlayerCharacterBuilder playerCharacter = DefaultPlayerCharacter.builder()
            .key(savedPlayerCharacter.getKey())
            .name(savedPlayerCharacter.getName())
            .animationSet(savedPlayerCharacter.getAnimationSetKey());
        for (SavedPlayerCharacter.StatValue statValue : savedPlayerCharacter.getStats()) {
            playerCharacter.statValue(statValue.getKey(), statValue.getValue());
        }

        return playerCharacter.build();
    }
}
