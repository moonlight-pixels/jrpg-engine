package com.github.jaystgelais.jrpg.combat.action;

public final class DefaultActions {

    private DefaultActions() {
    }

    public static ActionType createStandardSingleAttack(final DamageFormula damageFormula,
                                                        final ActionEffect additionalEffect) {
        return new ActionType(
                "Attack",
                new DamageEffect(damageFormula, additionalEffect),
                CombatTargetingData.STANDARD_SINGLE_ATTACK
        );
    }

    public static ActionType createStandardSingleAttack(final DamageFormula damageFormula) {
        return createStandardSingleAttack(damageFormula, null);
    }
}
