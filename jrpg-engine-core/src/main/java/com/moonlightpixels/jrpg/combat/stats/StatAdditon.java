package com.moonlightpixels.jrpg.combat.stats;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public final class StatAdditon extends StatModifier {
    private final int addition;

    /**
     * Creates an addition based modifier for a given stat.
     *
     * @param stat stat to modify
     * @param addition amount to add (or subtract)
     */
    public StatAdditon(final Stat.Key stat, final int addition) {
        super(stat);
        this.addition = addition;
    }

    @Override
    public Integer apply(final Integer value) {
        return value + addition;
    }
}
