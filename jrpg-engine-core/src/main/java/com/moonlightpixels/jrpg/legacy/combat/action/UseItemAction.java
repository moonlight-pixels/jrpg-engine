package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;
import com.moonlightpixels.jrpg.legacy.inventory.Item;
import com.google.common.base.Preconditions;

import java.util.LinkedList;
import java.util.List;

public final class UseItemAction extends CombatAction {
    private final Item item;
    private final List<Combatant> targets;

    UseItemAction(final Item item, final List<Combatant> targets) {
        Preconditions.checkArgument(item.canUseInBattle());
        Preconditions.checkNotNull(item.getAction());
        this.item = item;
        this.targets = targets;
    }

    @Override
    public List<CombatOutcome> perform(final Battle battle) {
        List<CombatOutcome> outcomes = new LinkedList<>();

        if (item.getAllowedTargets() == AllowedTargets.Untargeted) {
            outcomes.addAll(item.getAction().perform(null));
        } else {
            targets.forEach(target -> outcomes.addAll(item.getAction().perform(target)));
        }

        return outcomes;
    }
}
