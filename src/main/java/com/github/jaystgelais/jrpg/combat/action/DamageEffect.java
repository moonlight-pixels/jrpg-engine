package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.Combatant;
import com.google.common.base.Preconditions;

import java.util.Optional;

public final class DamageEffect implements ActionEffect {
    private final HitCalculation hitCalculation;
    private final DamageFormula damageFormula;
    private final ActionEffect additionalEffect;

    public DamageEffect(final HitCalculation hitCalculation, final DamageFormula damageFormula,
                        final ActionEffect additionalEffect) {
        Preconditions.checkNotNull(hitCalculation);
        Preconditions.checkNotNull(damageFormula);
        this.hitCalculation = hitCalculation;
        this.damageFormula = damageFormula;
        this.additionalEffect = additionalEffect;
    }

    @Override
    public void apply(final Battle battle, final Combatant performer, final Combatant target) {
        if (hitCalculation.didHit(performer, target)) {
            target.applyDamage(damageFormula.calculateDamage(performer, target));
            getAdditionalEffect().ifPresent(effect -> effect.apply(battle, performer, target));
        }
    }

    private Optional<ActionEffect> getAdditionalEffect() {
        return Optional.ofNullable(additionalEffect);
    }
}
