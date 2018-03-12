package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.event.CombatEvent;

public interface CombatEventListener {
    void onQueue(final CombatEvent combatEvent);
    void onStart(final CombatEvent combatEvent);
    void onComplete(final CombatEvent combatEvent);
}
