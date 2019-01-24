package com.moonlightpixels.jrpg.combat

import spock.lang.Specification

class AttackActionSpec extends Specification {
    void 'Produces a TargetMissOutcome when attack misses'() {
        setup:
        AttackAction attack = new AttackAction(
            { attacker, target  -> false },
            { attacker, target -> 1 }
        )
        Combatant attacker = Mock()
        Combatant target = Mock()

        when:
        CombatActionOutcome outcome = attack.calculateOutcome(attacker, [ target ])

        then:
        outcome instanceof TargetedActionOutcome
        ((TargetedActionOutcome) outcome).targetOutcomes[0].target == target
        ((TargetedActionOutcome) outcome).targetOutcomes[0] instanceof TargetMissOutcome
    }
}
