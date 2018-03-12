package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.outcome.CombatOutcome;

public interface CombatOutcomeListener {
    void onOutcome(CombatOutcome outcome);
}
