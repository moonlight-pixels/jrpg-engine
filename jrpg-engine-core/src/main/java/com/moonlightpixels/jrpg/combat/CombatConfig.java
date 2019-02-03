package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.render.internal.CombatLayout;

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

    /**
     * Compbat screen layout.
     *
     * @return layout
     */
    CombatLayout getCombatLayout();

    /**
     * Compbat screen layout.
     *
     * @param combatLayout layout
     */
    void setCombatLayout(CombatLayout combatLayout);
}
