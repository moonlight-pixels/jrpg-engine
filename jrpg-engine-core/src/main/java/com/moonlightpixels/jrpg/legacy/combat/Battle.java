package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.enemy.Enemy;
import com.moonlightpixels.jrpg.legacy.combat.event.CombatEvent;
import com.moonlightpixels.jrpg.legacy.combat.outcome.CombatOutcome;
import com.moonlightpixels.jrpg.legacy.party.Party;
import com.moonlightpixels.jrpg.legacy.party.PlayerCharacter;
import com.moonlightpixels.jrpg.legacy.state.State;
import com.moonlightpixels.jrpg.legacy.state.StateAdapter;
import com.moonlightpixels.jrpg.legacy.state.StateMachine;
import com.moonlightpixels.jrpg.legacy.state.Updatable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public final class Battle implements Updatable, PlayerInputHandler {
    private static final String STATE_READY = "ready";
    private static final String STATE_ACTIVE = "active";
    private static final String STATE_PARAM_EVENT = "event";

    private final PriorityQueue<CombatEvent> eventQueue = new PriorityQueue<>();
    private final List<MesageListener> mesageListeners = new LinkedList<>();
    private final List<CombatEventListener> eventListeners = new LinkedList<>();
    private final List<CombatOutcomeListener> outcomeListeners = new LinkedList<>();
    private final BattleSystem battleSystem;
    private final Party party;
    private final List<Enemy> enemies;
    private final StateMachine stateMachine;
    private long timeSinceLastTurnMs = 0L;

    public Battle(final BattleSystem battleSystem, final Party party, final List<Enemy> enemies) {
        this.battleSystem = battleSystem;
        this.party = party;
        this.enemies = enemies;
        stateMachine = initStateMachine();
        battleSystem.configureBattle(this);
    }

    public Party getParty() {
        return party;
    }

    public List<Enemy> getEnemies() {
        return enemies;
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
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final ActionTypeProvider provider) {
        battleSystem.handlePlayerInput(playerCharacter, provider);
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final TargetableChoiceProvider provider) {
        battleSystem.handlePlayerInput(playerCharacter, provider);
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final TargetChoiceProvider provider,
                                  final AllowedTargets allowedTargets) {
        battleSystem.handlePlayerInput(playerCharacter, provider, allowedTargets);
    }

    public Optional<List<? extends Combatant>> getFixedTargetsForAllowedTargets(final Combatant combatant,
                                                                                final AllowedTargets allowedTargets) {
        switch (allowedTargets) {
            case AllAllies:
                return Optional.of(getAllies(combatant));
            case AllEnemies:
                return Optional.of(getOpponents(combatant));
            case AllEither:
                return Optional.of(getCombatants());
            case Untargeted:
                return Optional.of(Collections.emptyList());
            default:
                return Optional.empty();
        }
    }

    public List<? extends Combatant> getEligibleTargets(final Combatant combatant,
                                                         final AllowedTargets allowedTargets) {
        switch (allowedTargets) {
            case SingleAllie:
            case AllAllies:
                return getAllies(combatant);
            case SingleEnemy:
            case AllEnemies:
                return getOpponents(combatant);
            case SingleEither:
            case AllEither:
                return getCombatants();
            default:
                return Collections.emptyList();
        }
    }

    private List<? extends Combatant> getCombatants() {
        return Lists.newArrayList(Iterables.concat(party.getMembers(), enemies));
    }

    public List<? extends Combatant> getAllies(final Combatant combatant) {
        if (combatant instanceof PlayerCharacter) {
            return party.getMembers();
        }

        return enemies;
    }

    public List<? extends Combatant> getOpponents(final Combatant combatant) {
        if (combatant instanceof PlayerCharacter) {
            return enemies;
        }

        return party.getMembers();
    }

    @Override
    public void update(final long elapsedTime) {
        timeSinceLastTurnMs += elapsedTime;
        if (timeSinceLastTurnMs > battleSystem.getTurnLengthMs()) {
            eventQueue.forEach(CombatEvent::tick);
        }
        stateMachine.update(elapsedTime);
    }

    public boolean isGameOver() {
        return party.getMembers().stream().allMatch(playerCharacter -> playerCharacter.getCurrentHp() <= 0);
    }

    public boolean isVictory() {
        return enemies.isEmpty();
    }

    private void clearDeadEnemies() {
        List<Enemy> deadEnemies = enemies.stream()
                .filter(enemy -> enemy.getCurrentHp() <= 0)
                .collect(Collectors.toList());
        enemies.removeAll(deadEnemies);
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
                clearDeadEnemies();
                eventListeners.forEach(combatEventListener -> combatEventListener.onComplete(combatEvent));
                timeSinceLastTurnMs = 0L;
                combatEvent = null;
            }
        };
    }
}
