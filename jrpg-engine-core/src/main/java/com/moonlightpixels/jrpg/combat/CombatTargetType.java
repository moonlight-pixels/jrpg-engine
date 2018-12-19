package com.moonlightpixels.jrpg.combat;

public enum CombatTargetType {
    /**
     * Action targets a single enemy.
     */
    SingleEnemy,
    /**
     * Action targets all enemies.
     */
    AllEnemy,
    /**
     * Action targets a single ally.
     */
    SingleAlly,
    /**
     * Action targets all allies.
     */
    AllAlly,
    /**
     * Action targets a single enemy or ally.
     */
    SingleAny,
    /**
     * Action targets action performer.
     */
    Self
}
