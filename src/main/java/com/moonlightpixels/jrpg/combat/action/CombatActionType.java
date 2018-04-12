package com.moonlightpixels.jrpg.combat.action;

import com.moonlightpixels.jrpg.combat.Combatant;

import java.util.List;

public abstract class CombatActionType<T extends CombatAction> {
    private final String name;

    protected CombatActionType(final String name) {
        this.name = name;
    }

    public abstract T createAction(Combatant actor, List<Combatant> targets);

    public abstract TargetableChoiceProvider<? extends Targetable> getTargetableChoiceProvider();

    public final String getName() {
        return name;
    }
}
