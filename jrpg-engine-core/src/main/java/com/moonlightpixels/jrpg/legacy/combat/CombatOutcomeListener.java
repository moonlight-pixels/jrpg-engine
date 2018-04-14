package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;

public interface CombatOutcomeListener {
    void onOutcome(CombatOutcome outcome);
}
