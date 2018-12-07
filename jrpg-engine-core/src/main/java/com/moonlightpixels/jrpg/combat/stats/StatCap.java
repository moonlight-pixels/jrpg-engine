package com.moonlightpixels.jrpg.combat.stats;

import lombok.Value;

import java.util.Arrays;
import java.util.List;

@Value
public final class StatCap {
    private final int cap;
    private final List<StatHolder.Type> appliesToTypes;

    /**
     * Creates a new StatCap that applies to given StatHolder Types.
     *
     * @param cap capped value
     * @param appliesToTypes applicable types
     */
    public StatCap(final int cap, final StatHolder.Type... appliesToTypes) {
        this.cap = cap;
        this.appliesToTypes = Arrays.asList(appliesToTypes);
    }

    /**
     * Returns true if this cap applies to the given StatHolder.
     *
     * @param statHolder StatHolder to check for applicability
     * @return true if this cap applies to the given StatHolder
     */
    public boolean appliesTo(final StatHolder statHolder) {
        return appliesToTypes.contains(statHolder.getHolderType());
    }
}
