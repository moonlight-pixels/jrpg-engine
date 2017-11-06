package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.party.Party;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public final class Battle {
    private final PriorityQueue<CombatEvent> eventQueue = new PriorityQueue<>();
    private final Queue<String> messageQueue = new LinkedList<>();
    private final Set<Combatant> combatants = new HashSet<>();
    private final Party party;

    public Battle(final Party party) {
        this.party = party;
    }

    void queueMessage(final String message) {
        messageQueue.offer(message);
    }

    void addCombatant(final Combatant combatant) {
        combatants.add(combatant);
    }

    void removeCombatant(final Combatant combatant) {
        combatants.remove(combatant);
        eventQueue.removeIf(combatEvent -> combatEvent.getOwner().equals(Optional.of(combatant)));
    }
}
