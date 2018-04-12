package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.outcome.CombatOutcome;

public interface CombatOutcomeListener {
    void onOutcome(CombatOutcome outcome);
}
