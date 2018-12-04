package com.moonlightpixels.jrpg.player;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.equipment.EquipmentSlot;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Delegate;

import java.util.List;

@Data
public final class PlayerCharacter {
    private final Key key;
    private String name;
    private CharacterAnimationSet.Key animationSet;
    private final NextLevelFunction nextLevelFunction;
    private int experience = 0;
    @Delegate
    private final PlayerStats playerStats;

    @Builder
    private PlayerCharacter(final Key key,
                            final String name,
                            final CharacterAnimationSet.Key animationSet,
                            final NextLevelFunction nextLevelFunction,
                            final int experience,
                            final List<EquipmentSlot> equipmentSlots) {
        this.key = key;
        this.name = name;
        this.animationSet = animationSet;
        this.nextLevelFunction = nextLevelFunction;
        this.experience = experience;
        this.playerStats = new PlayerStats(equipmentSlots);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public interface Key { }
}
