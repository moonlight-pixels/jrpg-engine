package com.moonlightpixels.jrpg.combat;

public interface CombatAI {
    /**
     * Return next Combat Action based on state of battle.
     *
     * @param battle Battle state
     * @return CombatActionInstance
     */
    CombatActionInstance getNextAction(Battle battle);
}
