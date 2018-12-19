package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

@Data
public final class Enemy {
    private final Key key;
    private final String name;
    private final Map<Stat.Key, Integer> statValues;
    private final int experience;
    private final CombatAI combatAI;

    @Builder
    private Enemy(final Key key,
                  final String name,
                  @Singular final Map<Stat.Key, Integer> stats,
                  final int experience,
                  final CombatAI combatAI) {
        this.key = key;
        this.name = name;
        this.statValues = stats;
        this.experience = experience;
        this.combatAI = combatAI;
    }

    public interface Key { }
}
