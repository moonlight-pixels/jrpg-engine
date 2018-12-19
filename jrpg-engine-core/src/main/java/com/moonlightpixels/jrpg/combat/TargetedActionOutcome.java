package com.moonlightpixels.jrpg.combat;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
final class TargetedActionOutcome extends CombatActionOutcome {
    private final List<CombatTargetOutcome> targetOutcomes;

    @Builder
    TargetedActionOutcome(final CombatAction.Key action,
                          final Combatant combatant,
                          @Singular final List<CombatTargetOutcome> targetOutcomes) {
        super(action, combatant);
        this.targetOutcomes = targetOutcomes;
    }

    @Override
    void apply() {
        targetOutcomes.forEach(CombatTargetOutcome::apply);
    }
}
