package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.inventory.Item;

import java.util.List;

public final class UseItemActionType extends CombatActionType<UseItemAction> {
    private TargetableChoiceProvider<Item> choiceProvider;

    protected UseItemActionType() {
        super("Item");
    }

    @Override
    public UseItemAction createAction(final Combatant actor, final List<Combatant> targets) {
        final UseItemAction action = new UseItemAction(choiceProvider.getChoice(), targets);
        choiceProvider = null;
        return action;
    }

    @Override
    public TargetableChoiceProvider<Item> getTargetableChoiceProvider() {
        choiceProvider = new TargetableChoiceProvider<>(Item.class);
        return choiceProvider;
    }
}
