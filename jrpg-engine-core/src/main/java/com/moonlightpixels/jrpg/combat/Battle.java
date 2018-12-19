package com.moonlightpixels.jrpg.combat;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.combat.internal.BattleEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public final class Battle {
    private final CombatConfig config;
    private final MessageDispatcher messageDispatcher;
    private final List<Combatant> party;
    private final List<Combatant> enemies;
    private final PriorityQueue<BattleEvent> eventQueue = new PriorityQueue<>();
    private final StateMachine<Battle, BattleState> stateMachine;
    private float timeUntilNextTick;
    private DecisionHandler activeDecision;
    private CombatActionOutcome activeAction;

    @Inject
    Battle(final CombatConfig config,
           @Named("combat") final MessageDispatcher messageDispatcher) {
        this.config = config;
        this.messageDispatcher = messageDispatcher;
        party = Collections.emptyList();
        enemies = Collections.emptyList();
        timeUntilNextTick = config.getTimePerTick();
        stateMachine = new DefaultStateMachine<>(this, BattleState.CheckQueue);
        messageDispatcher.addListener(
            stateMachine,
            CombatMessageTypes.ANIMATION_COMPLETE
        );
    }

    void update(final float elapsedTime) {
        timeUntilNextTick -= elapsedTime;
        if (timeUntilNextTick < 0f) {
            tick();
            timeUntilNextTick = config.getTimePerTick();
        }
        stateMachine.update();
    }

    void pruneDeadEnemies() {
        enemies.removeIf(Combatant::isDead);
    }

    private void changeStateAndUpdate(final BattleState state) {
        stateMachine.changeState(state);
        stateMachine.update();
    }

    private void tick() {
        eventQueue.forEach(BattleEvent::tick);
        Stream.concat(party.stream(), enemies.stream())
            .map(Combatant::getCombatTurnMeter)
            .forEach(CombatTurnMeter::tick);
        Stream.concat(party.stream(), enemies.stream())
            .filter(combatant -> combatant.getCombatTurnMeter().isFull())
            .map(combatant -> BattleEvent.builder()
                .type(BattleEvent.Type.Decision)
                .combatant(combatant)
                .ticks(0)
                .build()
            )
            .forEach(eventQueue::add);
    }

    private Optional<BattleEvent> getNextEvent() {
        Preconditions.checkState(!eventQueue.isEmpty(), "EventQueue cannot be Empty!");
        return (eventQueue.peek().canStart()) ? Optional.of(eventQueue.poll()) : Optional.empty();
    }

    private enum BattleState implements State<Battle> {
        CheckQueue {
            @Override
            public void update(final Battle entity) {
                entity.getNextEvent().ifPresent(battleEvent -> {
                    switch (battleEvent.getType()) {
                        case Decision:
                            entity.activeDecision = battleEvent.getCombatant().getDecisionHandler();
                            entity.changeStateAndUpdate(Decision);
                            break;
                        case Action:
                            entity.activeAction = ((CombatActionInstance) battleEvent.getPayload()).getOutcome();
                            entity.changeStateAndUpdate(AminateAction);
                            break;
                        default:
                            // Do nothing
                    }
                });
            }
        },
        Decision {
            @Override
            public void update(final Battle entity) {
                entity.activeDecision.getDecision(entity).ifPresent(decision -> {
                    BattleEvent event = BattleEvent.builder()
                        .type(BattleEvent.Type.Action)
                        .combatant(decision.getCombatant())
                        .ticks(decision.getDelayInTicks())
                        .payload(decision)
                        .build();
                    entity.eventQueue.add(event);
                    entity.changeStateAndUpdate(CheckQueue);
                });
            }

            @Override
            public void exit(final Battle entity) {
                entity.activeDecision = null;
            }
        },
        AminateAction {
            @Override
            public void enter(final Battle entity) {
                entity.messageDispatcher.dispatchMessage(CombatMessageTypes.START_ANIMATION, entity.activeAction);
            }

            @Override
            public boolean onMessage(final Battle entity, final Telegram telegram) {
                if (telegram.message == CombatMessageTypes.ANIMATION_COMPLETE) {
                    entity.changeStateAndUpdate(ActionResults);
                    return true;
                }
                return false;
            }

            @Override
            public void update(final Battle entity) { }
        },
        ActionResults {
            @Override
            public void update(final Battle entity) {
                entity.activeAction.apply();
                entity.activeAction.getCombatant().getCombatTurnMeter().reset();
                entity.pruneDeadEnemies();
                if (entity.party.stream().allMatch(Combatant::isDead)) {
                    entity.changeStateAndUpdate(GameOver);
                } else if (entity.enemies.isEmpty()) {
                    entity.changeStateAndUpdate(RewardSummary);
                }
            }
        },
        GameOver {
            @Override
            public void update(final Battle entity) {

            }
        },
        RewardSummary {
            @Override
            public void update(final Battle entity) {

            }
        };

        @Override
        public void enter(final Battle entity) { }

        @Override
        public void exit(final Battle entity) { }

        @Override
        public boolean onMessage(final Battle entity, final Telegram telegram) {
            return false;
        }
    }
}
