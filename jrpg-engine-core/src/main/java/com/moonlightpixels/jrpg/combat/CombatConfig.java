package com.moonlightpixels.jrpg.combat;

public interface CombatConfig {
    /**
     * Retruns the number of seconds per 'tick' in combat.
     *
     * @return seconds as float
     */
    float getTimePerTick();

    /**
     * Sets the number of seconds per 'tick' in combat.
     *
     * @param timePerTick seconds as float
     */
    void setTimePerTick(float timePerTick);
}
