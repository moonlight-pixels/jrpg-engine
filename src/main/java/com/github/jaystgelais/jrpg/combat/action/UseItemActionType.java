package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.inventory.Item;

import java.util.List;

public final class UseItemActionType extends CombatActionType<UseItemAction> {
    private final TargetableChoiceProvider<Item> choiceProvider;

    protected UseItemActionType() {
        super("Item");
        choiceProvider = new TargetableChoiceProvider<>();
    }

    @Override
    public UseItemAction createAction(final Combatant actor, final List<Combatant> targets) {
        return new UseItemAction(choiceProvider.getChoice(), targets);
    }

    @Override
    public TargetableChoiceProvider<Item> getTargetableChoiceProvider() {
        return choiceProvider;
    }
}
