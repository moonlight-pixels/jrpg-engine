package com.moonlightpixels.jrpg.combat;

import java.util.Optional;

interface DecisionHandler {
    Optional<CombatActionInstance> getDecision(Battle battle);
}
