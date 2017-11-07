package com.github.jaystgelais.jrpg.combat;

public interface CombatEventListener {
    void onQueue(final CombatEvent combatEvent);
    void onStart(final CombatEvent combatEvent);
    void onComplete(final CombatEvent combatEvent);
}
