package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatMeter;

public interface Combatant {
    /**
     * Get Combatant's Hit Point meter.
     *
     * @return hit point meter
     */
    StatMeter getHitPoints();

    /**
     * Get Combatant's meter by max value stat.
     *
     * @param maxValueKey Key for max value stat
     * @return meter
     */
    StatMeter getMeter(Stat.Key maxValueKey);

    /**
     * Get value of a stat.
     *
     * @param key Key for stat
     * @return stat value
     */
    int getStatValue(Stat.Key key);

    /**
     * Get DecisionHandler for combatants combat turn.
     *
     * @return DecisionHandler
     */
    DecisionHandler getDecisionHandler();

    /**
     * Get Combatant's CombatTurnMeter, which tracks time to the Combatant's next turn.
     *
     * @return CombatTurnMeter
     */
    CombatTurnMeter getCombatTurnMeter();

    /**
     * Returns true if Combatant is dead.
     *
     * @return true if dead
     */
    default boolean isDead() {
        return getHitPoints().getValue() == 0;
    }
}
