package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.render.BattleAnimation;
import lombok.Data;

@Data
public abstract class CombatActionOutcome {
    private final CombatAction.Key action;
    private final Combatant combatant;

    CombatActionOutcome(final CombatAction.Key action, final Combatant combatant) {
        this.action = action;
        this.combatant = combatant;
    }

    abstract void apply();

    /**
     * Animation for this outcome.
     *
     * @return animation
     */
    public abstract BattleAnimation getAnimation();
}
