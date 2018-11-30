package com.moonlightpixels.jrpg.combat.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class StatMeter {
    private final StatHolder statHolder;
    private final Stat maxValue;
    private int value;

    /**
     * Get current value of meter.
     *
     * @return current value
     */
    public int getValue() {
        // ensure value is normalized to Max in case Max has changed
        value = normalizeToMax(value);
        return value;
    }

    /**
     * Set meter value.
     *
     * @param value new value
     */
    public void setValue(final int value) {
        this.value = normalize(value);
    }

    /**
     * Add (or subtract) a value from this meter.
     *
     * @param addition amount to add (or subtract) to meter's value
     */
    public void add(final int addition) {
        value = normalize(value + addition);
    }

    /**
     * Apply a multiplier to this meter.
     *
     * @param multiplier ampunt to multiply this meter's value by
     */
    public void mulitply(final float multiplier) {
        value = normalize(Math.round(value * multiplier));
    }

    private int normalize(final int value) {
        return normalizeToFloor(normalizeToMax(value));
    }

    private int normalizeToFloor(final int value) {
        return Math.max(0, value);
    }

    private int normalizeToMax(final int value) {
        return Math.min(maxValue.getValue(statHolder), value);
    }
}
