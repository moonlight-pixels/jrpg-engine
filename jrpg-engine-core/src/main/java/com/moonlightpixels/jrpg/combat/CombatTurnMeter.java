package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.stats.RequiredStats;
import com.moonlightpixels.jrpg.combat.stats.StatHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMeter;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;

public final class CombatTurnMeter {
    private final StatMeter meter;

    CombatTurnMeter(final StatSystem statSystem, final StatHolder statHolder) {
        meter = new StatMeter(statHolder, statSystem.getStat(RequiredStats.CombatTurnInterval), 0);
    }

    void tick() {
        meter.add(1);
    }

    boolean isFull() {
        return meter.isFull();
    }

    void reset() {
        meter.setValue(0);
    }

    /**
     * Get percentage this meter is full (as a float between 0f and 1.0f).
     *
     * @return percentage as float
     */
    public float getPercentFull() {
        return meter.getValue() / (float) meter.getMaxValue();
    }
}
