package com.moonlightpixels.jrpg.combat.stats;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class StatMeter {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final StatHolder statHolder;
    private final Stat maxValue;
    private int value;

    /**
     * Constructs a new StatMeter.
     *
     * @param statHolder StatHolder this meter belongs to
     * @param maxValue Stat representing this meter's max value
     * @param value current value of meter
     */
    public StatMeter(final StatHolder statHolder, final Stat maxValue, final int value) {
        this.statHolder = statHolder;
        this.maxValue = maxValue;
        this.value = value;
    }

    /**
     * Constructs a new StatMeter.
     *
     * @param statHolder StatHolder this meter belongs to
     * @param maxValue Stat representing this meter's max value
     */
    public StatMeter(final StatHolder statHolder, final Stat maxValue) {
        this(statHolder, maxValue, maxValue.getValue(statHolder));
    }

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
     * Returns Max Value of this meter.
     * @return max value
     */
    public int getMaxValue() {
        return maxValue.getValue(statHolder);
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

    /**
     * Returns true if value is equal to max value.
     *
     * @return true if value is equal to max value
     */
    public boolean isFull() {
        return value == maxValue.getValue(statHolder);
    }

    /**
     * Returns true if value is equal to 0.
     *
     * @return true if value is equal to 0
     */
    public boolean isEmpty() {
        return value == 0;
    }

    private int normalize(final int value) {
        return normalizeToFloor(normalizeToMax(value));
    }

    private int normalizeToFloor(final int value) {
        return Math.max(0, value);
    }

    private int normalizeToMax(final int value) {
        return Math.min(getMaxValue(), value);
    }
}
