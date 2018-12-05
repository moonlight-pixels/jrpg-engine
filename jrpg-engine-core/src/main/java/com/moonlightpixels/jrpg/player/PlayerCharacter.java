package com.moonlightpixels.jrpg.player;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.internal.inject.InjectionContext;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.equipment.internal.EquipmentSlotConfig;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Delegate;

import java.util.Map;

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
                            @Singular final Map<Stat.Key, Integer> statValues) {
        this(
            key,
            name,
            animationSet,
            nextLevelFunction,
            experience,
            new PlayerStats(
                InjectionContext.get().getInstance(EquipmentSlotConfig.class).getEquipmentSlots()
            )
        );
        statValues.forEach(playerStats::setBaseValue);
    }

    PlayerCharacter(final Key key,
                    final String name,
                    final CharacterAnimationSet.Key animationSet,
                    final NextLevelFunction nextLevelFunction,
                    final int experience,
                    final PlayerStats playerStats) {
        this.key = key;
        this.name = name;
        this.animationSet = animationSet;
        this.nextLevelFunction = nextLevelFunction;
        this.experience = experience;
        this.playerStats = playerStats;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public interface Key { }
}
