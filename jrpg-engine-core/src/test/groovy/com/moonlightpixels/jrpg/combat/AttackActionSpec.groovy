package com.moonlightpixels.jrpg.combat

import spock.lang.Specification

import java.util.function.BiFunction

class AttackActionSpec extends Specification {
    BiFunction<Combatant, Combatant, Boolean> toHitFormula
    BiFunction<Combatant, Combatant, Integer> damageFormula
    AttackAction attack
    Combatant attacker
    Combatant target

    void setup() {
        toHitFormula = Mock()
        damageFormula = Mock()
        attack = new AttackAction(toHitFormula, damageFormula)
        attacker = Mock()
        target = Mock()
    }

    void 'Attack has zero delay'() {
        expect:
        attack.getDelayInTicks(attacker) == 0
    }

    void 'Produces a TargetMissOutcome when attack misses'() {
        setup:
        toHitFormula.apply(attacker, target) >> false

        when:
        CombatActionOutcome outcome = attack.calculateOutcome(attacker, [ target ])

        then:
        outcome instanceof TargetedActionOutcome
        ((TargetedActionOutcome) outcome).targetOutcomes[0].target == target
        ((TargetedActionOutcome) outcome).targetOutcomes[0] instanceof TargetMissOutcome
    }

    void 'applies damage when attack hits'() {
        setup:
        toHitFormula.apply(attacker, target) >> true
        damageFormula.apply(attacker, target) >> 10

        when:
        CombatActionOutcome outcome = attack.calculateOutcome(attacker, [ target ])

        then:
        outcome instanceof TargetedActionOutcome
        ((TargetedActionOutcome) outcome).targetOutcomes[0].target == target
        ((TargetedActionOutcome) outcome).targetOutcomes[0] instanceof TargetDamageOutcome
        ((TargetDamageOutcome) ((TargetedActionOutcome) outcome).targetOutcomes[0]).damage == 10
    }
}
