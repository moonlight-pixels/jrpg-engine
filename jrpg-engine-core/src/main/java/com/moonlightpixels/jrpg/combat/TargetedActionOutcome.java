package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.render.BattleAnimation;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
final class TargetedActionOutcome extends CombatActionOutcome {
    private final List<CombatTargetOutcome> targetOutcomes;
    private final BattleAnimation animation;

    @Builder
    TargetedActionOutcome(final CombatAction.Key action,
                          final Combatant combatant,
                          @Singular final List<CombatTargetOutcome> targetOutcomes,
                          final BattleAnimation animation) {
        super(action, combatant);
        this.targetOutcomes = targetOutcomes;
        this.animation = animation;
    }

    @Override
    void apply() {
        targetOutcomes.forEach(CombatTargetOutcome::apply);
    }

    @Override
    public BattleAnimation getAnimation() {
        return animation;
    }
}
