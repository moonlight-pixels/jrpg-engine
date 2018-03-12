package com.github.jaystgelais.jrpg.combat.event;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.Combatant;

public final class CombatTurnEvent extends CombatEvent implements CombatantEvent {
    private final Combatant combatant;

    public CombatTurnEvent(final Combatant combatant, final int ticksRemaining) {
        super(ticksRemaining);
        this.combatant = combatant;
    }

    @Override
    public void start(final Battle battle) {

    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public Combatant getCombatant() {
        return combatant;
    }
}
