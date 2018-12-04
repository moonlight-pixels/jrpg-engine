package com.moonlightpixels.jrpg.combat.stats;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public final class StatMultiplier extends StatModifier {
    private final float multiplier;

    /**
     * Creates an multiplier based modifier for a given stat.
     *
     * @param stat stat to modify
     * @param multiplier amount to multiply stat value by
     */
    public StatMultiplier(final Stat.Key stat, final float multiplier) {
        super(stat);
        this.multiplier = multiplier;
    }

    @Override
    public Integer apply(final Integer value) {
        return Math.round(value * multiplier);
    }
}
