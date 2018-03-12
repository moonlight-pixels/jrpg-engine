package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.event.CombatEvent;
import com.github.jaystgelais.jrpg.combat.outcome.CombatOutcome;
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

public final class Battle implements Updatable {
    private static final String STATE_READY = "ready";
    private static final String STATE_ACTIVE = "active";
    private static final String STATE_PARAM_EVENT = "event";

    private final PriorityQueue<CombatEvent> eventQueue = new PriorityQueue<>();
    private final List<MesageListener> mesageListeners = new LinkedList<>();
    private final List<CombatEventListener> eventListeners = new LinkedList<>();
    private final List<CombatOutcomeListener> outcomeListeners = new LinkedList<>();
    private final BattleSystem battleSystem;
    private final Party party;
    private final StateMachine stateMachine;
    private long timeSinceLastTurnMs = 0L;

    public Battle(final BattleSystem battleSystem, final Party party) {
        this.battleSystem = battleSystem;
        this.party = party;
        stateMachine = initStateMachine();
    }

    public void addMessageListener(final MesageListener mesageListener) {
        mesageListeners.add(mesageListener);
    }

    public void addCombatEventListener(final CombatEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public void addCombatOutcomeListener(final CombatOutcomeListener outcomeListener) {
        outcomeListeners.add(outcomeListener);
    }

    public void queueEvent(final CombatEvent combatEvent) {
        eventQueue.offer(combatEvent);
        eventListeners.forEach(combatEventListener -> combatEventListener.onQueue(combatEvent));
    }

    public void postMessage(final String message) {
        mesageListeners.forEach(mesageListener -> mesageListener.onMessage(message));
    }

    public void postOutcome(final CombatOutcome outcome) {
        outcomeListeners.forEach(outcomeListener -> outcomeListener.onOutcome(outcome));
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
