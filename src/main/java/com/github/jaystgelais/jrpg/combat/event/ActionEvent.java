package com.github.jaystgelais.jrpg.combat.event;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.combat.action.CombatAction;

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
