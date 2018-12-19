package com.moonlightpixels.jrpg.combat;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
public final class CombatActionInstance {
    private final Combatant combatant;
    private final CombatAction action;
    private final List<Combatant> targets;

    @Builder
    CombatActionInstance(final Combatant combatant,
                         final CombatAction action,
                         @Singular final List<Combatant> targets) {
        this.combatant = combatant;
        this.action = action;
        this.targets = targets;
    }

    int getDelayInTicks() {
        return action.getDelayInTicks(combatant);
    }

    CombatActionOutcome getOutcome() {
        return action.calculateOutcome(combatant, targets);
    }
}
