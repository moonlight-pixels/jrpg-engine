package com.moonlightpixels.jrpg.legacy.combat.event;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.combat.action.CombatAction;

public final class ActionEvent extends CombatEvent implements CombatantEvent {
    private final Combatant combatant;
    private final CombatAction action;

    public ActionEvent(final Combatant combatant, final CombatAction action, final int ticksRemaining) {
        super(ticksRemaining);
        this.combatant = combatant;
        this.action = action;
    }

    @Override
    public void start(final Battle battle) {
        action.start(battle);
    }

    @Override
    public boolean isComplete() {
        return action.isComplete();
    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public Combatant getCombatant() {
        return combatant;
    }
}
