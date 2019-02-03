package com.moonlightpixels.jrpg.combat;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class AttackAction extends CombatAction {
    /**
     * Default name given to this Action if one is not supplied.
     */
    public static final String DEFAULT_NAME = "Attack";

    private final BiFunction<Combatant, Combatant, Boolean> toHitFormula;
    private final BiFunction<Combatant, Combatant, Integer> damageFormula;

    /**
     * Creates a new AttackAction.
     *
     * @param name name given to this AttackAction
     * @param toHitFormula formula for calculating hit/miss
     * @param damageFormula formula for calculating damage
     */
    public AttackAction(final String name,
                        final BiFunction<Combatant, Combatant, Boolean> toHitFormula,
                        final BiFunction<Combatant, Combatant, Integer> damageFormula) {
        super(StandardCombatActions.Attack, name, CombatTargetType.SingleEnemy);
        this.toHitFormula = toHitFormula;
        this.damageFormula = damageFormula;
    }

    /**
     * Creates a new AttackAction (using the default name).
     *
     * @param toHitFormula formula for calculating hit/miss
     * @param damageFormula formula for calculating damage
     */
    public AttackAction(final BiFunction<Combatant, Combatant, Boolean> toHitFormula,
                        final BiFunction<Combatant, Combatant, Integer> damageFormula) {
        this(DEFAULT_NAME, toHitFormula, damageFormula);
    }

    @Override
    protected int getDelayInTicks(final Combatant combatant) {
        return 0;
    }

    @Override
    protected CombatActionOutcome calculateOutcome(final Combatant combatant, final List<Combatant> targets) {
        return new TargetedActionOutcome(
            getKey(),
            combatant,
            targets.stream()
                .map(target -> applyAttack(combatant, target))
                .collect(Collectors.toList()),
            null); // TODO fix this
    }

    private CombatTargetOutcome applyAttack(final Combatant combatant, final Combatant target) {
        return (toHitFormula.apply(combatant, target))
            ? new TargetDamageOutcome(target, damageFormula.apply(combatant, target))
            : new TargetMissOutcome(target);
    }
}
