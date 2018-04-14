package com.moonlightpixels.jrpg.legacy.combat.action;

import com.moonlightpixels.jrpg.legacy.combat.Battle;
import com.moonlightpixels.jrpg.legacy.combat.Combatant;
import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;
import com.moonlightpixels.jrpg.legacy.combat.outcome.DamageOutcome;
import com.moonlightpixels.jrpg.legacy.combat.outcome.DodgeOutcome;

import java.util.Collections;
import java.util.List;

public class SingleTargetAttack extends CombatAction {
    private final Combatant attacker;
    private final Combatant target;
    private final HitCalculation hitCalculation;
    private final DamageFormula damageFormula;

    public SingleTargetAttack(final Combatant attacker, final Combatant target,
                              final HitCalculation hitCalculation, final DamageFormula damageFormula) {
        this.attacker = attacker;
        this.target = target;
        this.hitCalculation = hitCalculation;
        this.damageFormula = damageFormula;
    }

    @Override
    public final List<CombatOutcome> perform(final Battle battle) {
        if (hitCalculation.didHit(attacker, target)) {
            return Collections.singletonList(
                    new DamageOutcome(target, damageFormula.calculateDamage(attacker, target))
            );
        }

        return Collections.singletonList(new DodgeOutcome(target));
    }
}
