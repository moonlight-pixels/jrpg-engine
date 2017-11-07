package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.party.Party;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public final class Battle implements Updatable {
    private static final String STATE_READY = "ready";
    private static final String STATE_ACTIVE = "active";
    private static final String STATE_PARAM_EVENT = "event";

    private final PriorityQueue<CombatEvent> eventQueue = new PriorityQueue<>();
    private final Queue<String> messageQueue = new LinkedList<>();
    private final Set<Combatant> combatants = new HashSet<>();
    private final List<CombatEventListener> eventListeners = new LinkedList<>();
    private final BattleSystem battleSystem;
    private final Party party;
    private final StateMachine stateMachine;
    private long timeSinceLastTurnMs = 0L;

    public Battle(final BattleSystem battleSystem, final Party party) {
        this.battleSystem = battleSystem;
        this.party = party;
        stateMachine = initStateMachine();
    }

    void queueEvent(final CombatEvent combatEvent) {
        eventQueue.offer(combatEvent);
        eventListeners.forEach(combatEventListener -> combatEventListener.onQueue(combatEvent));
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

    @Override
    public void update(final long elapsedTime) {
        timeSinceLastTurnMs += elapsedTime;
        if (timeSinceLastTurnMs > battleSystem.getTurnLengthMs()) {
            eventQueue.forEach(CombatEvent::tick);
        }
        stateMachine.update(elapsedTime);
    }

    private StateMachine initStateMachine() {
        return new StateMachine(
                new HashSet<State>(
                        Arrays.asList(
                                createReadyState(),
                                createActiveState()
                        )
                ),
                STATE_READY
        );
    }

    private State createReadyState() {
        return new StateAdapter() {

            @Override
            public String getKey() {
                return STATE_READY;
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                checkQueue();
            }

            @Override
            public void update(final long elapsedTime) {
                checkQueue();
            }

            private void checkQueue() {
                Optional.ofNullable(eventQueue.peek()).ifPresent(combatEvent -> {
                    if (combatEvent.canStart()) {
                        stateMachine.change(
                                STATE_ACTIVE,
                                Collections.singletonMap(STATE_PARAM_EVENT, eventQueue.poll())
                        );
                    }
                });
            }
        };
    }

    private State createActiveState() {
        final Battle battle = this;

        return new StateAdapter() {
            private CombatEvent combatEvent;

            @Override
            public String getKey() {
                return STATE_ACTIVE;
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                combatEvent = (CombatEvent) params.get(STATE_PARAM_EVENT);
                combatEvent.start(battle);
                eventListeners.forEach(combatEventListener -> combatEventListener.onStart(combatEvent));
            }

            @Override
            public void update(final long elapsedTime) {
                combatEvent.update(elapsedTime);
                if (combatEvent.isComplete()) {
                    stateMachine.change(STATE_ACTIVE);
                }
            }

            @Override
            public void onExit() {
                eventListeners.forEach(combatEventListener -> combatEventListener.onComplete(combatEvent));
                timeSinceLastTurnMs = 0L;
                combatEvent = null;
            }
        };
    }
}
