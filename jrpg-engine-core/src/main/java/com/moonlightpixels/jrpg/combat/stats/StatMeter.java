package com.moonlightpixels.jrpg.combat.stats;

import lombok.Data;

@Data
public final class StatMeter {
    private final StatHolder statHolder;
    private final Stat maxValue;
    private int value;

    /**
     * Add (or subtract) a value from this meter.
     *
     * @param addition amount to add (or subtract) to meter's value
     */
    public void add(final int addition) {
        value = Math.max(0, Math.min(maxValue.getValue(statHolder), value + addition));
    }

    /**
     * Apply a multiplier to this meter.
     *
     * @param multiplier ampunt to multiply this meter's value by
     */
    public void mulitply(final float multiplier) {
        value = Math.max(0, Math.min(maxValue.getValue(statHolder), Math.round(value * multiplier)));
    }
}
