package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.combat.outcome.CombatOutcome;
import com.github.jaystgelais.jrpg.inventory.Item;
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
