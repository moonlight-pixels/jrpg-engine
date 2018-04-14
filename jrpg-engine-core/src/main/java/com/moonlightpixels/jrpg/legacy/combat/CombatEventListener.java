package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.combat.event.CombatEvent;

public interface CombatEventListener {
    void onQueue(final CombatEvent combatEvent);
    void onStart(final CombatEvent combatEvent);
    void onComplete(final CombatEvent combatEvent);
}
