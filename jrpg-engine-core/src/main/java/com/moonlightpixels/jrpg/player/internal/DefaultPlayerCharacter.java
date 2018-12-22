package com.moonlightpixels.jrpg.player.internal;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.internal.inject.InjectionContext;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.NextLevelFunction;
import com.moonlightpixels.jrpg.player.PlayerCharacter;
import com.moonlightpixels.jrpg.player.equipment.internal.EquipmentSlotConfig;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Delegate;

import java.util.Map;

@Data
public final class DefaultPlayerCharacter implements PlayerCharacter {
    private final Key key;
    private String name;
    private CharacterAnimationSet.Key animationSet;
    private final NextLevelFunction nextLevelFunction;
    private int experience = 0;
    @Delegate
    @Getter(AccessLevel.PRIVATE)
    private final PlayerStats playerStats;

    @Builder
    private DefaultPlayerCharacter(final Key key,
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
                InjectionContext.get().getInstance(StatSystem.class),
                InjectionContext.get().getInstance(EquipmentSlotConfig.class).getEquipmentSlots(),
                statValues
            )
        );
    }

    DefaultPlayerCharacter(final Key key,
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

    public static class DefaultPlayerCharacterBuilder implements PlayerCharacter.PlayerCharacterBuilder { }

}
