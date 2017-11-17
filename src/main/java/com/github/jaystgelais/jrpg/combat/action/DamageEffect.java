package com.github.jaystgelais.jrpg.combat.action;

import com.github.jaystgelais.jrpg.combat.Combatant;
import com.google.common.base.Preconditions;

import java.util.Optional;

public final class DamageEffect implements ActionEffect {
    private final DamageFormula damageFormula;
    private final ActionEffect additionalEffect;

    public DamageEffect(final DamageFormula damageFormula, final ActionEffect additionalEffect) {
        Preconditions.checkNotNull(damageFormula);
        this.damageFormula = damageFormula;
        this.additionalEffect = additionalEffect;
    }

    @Override
    public void apply(final Combatant performer, final Combatant target) {
        target.applyDamage(damageFormula.calculateDamage(performer, target));
        getAdditionalEffect().ifPresent(effect -> effect.apply(performer, target));
    }

    private Optional<ActionEffect> getAdditionalEffect() {
        return Optional.ofNullable(additionalEffect);
    }
}
