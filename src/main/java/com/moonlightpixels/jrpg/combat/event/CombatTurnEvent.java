package com.moonlightpixels.jrpg.combat.event;

import com.moonlightpixels.jrpg.combat.Battle;
import com.moonlightpixels.jrpg.combat.Combatant;
import com.moonlightpixels.jrpg.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.combat.action.CombatActionType;
import com.moonlightpixels.jrpg.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.state.State;
import com.moonlightpixels.jrpg.state.StateAdapter;
import com.moonlightpixels.jrpg.state.StateMachine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class CombatTurnEvent extends CombatEvent implements CombatantEvent {
    private final Combatant combatant;
    private final StateMachine stateMachine;
    private boolean complete = false;
    private Battle battle;
    private CombatActionType actionType;

    public CombatTurnEvent(final Combatant combatant, final int ticksRemaining) {
        super(ticksRemaining);
        this.combatant = combatant;
        stateMachine = initStatemachine();
    }

    @Override
    public void start(final Battle battle) {
        this.battle = battle;
        stateMachine.change(
                "selectActionType",
                Collections.singletonMap("actionTypeProvider", combatant.getActionTypeProvider(battle))
        );
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    private void complete() {
        complete = true;
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public Combatant getCombatant() {
        return combatant;
    }

    private StateMachine initStatemachine() {
        final CombatTurnEvent event = this;
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "initial";
            }
        });
        states.add(new StateAdapter() {
            private ActionTypeProvider actionTypeProvider;

            @Override
            public String getKey() {
                return "selectActionType";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                actionTypeProvider = (ActionTypeProvider) params.get("actionTypeProvider");
            }

            @Override
            public void update(final long elapsedTime) {
                if (actionTypeProvider.isComplete()) {
                    actionType = actionTypeProvider.getActionType();
                    stateMachine.change(
                            "selectTargetable",
                            Collections.singletonMap(
                                    "targetableChoiceProvider",
                                    combatant.getTargetableChoiceProvider(actionType, battle)
                            )
                    );
                }
            }
        });
        states.add(new StateAdapter() {
            private TargetableChoiceProvider provider;

            @Override
            public String getKey() {
                return "selectTargetable";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                provider = (TargetableChoiceProvider) params.get("targetableChoiceProvider");
            }

            @Override
            public void update(final long elapsedTime) {
                if (provider.isComplete()) {
                    stateMachine.change(
                            "selectTargets",
                            Collections.singletonMap(
                                    "targetChoiceProvider",
                                    combatant.getTargetChoiceProvider(provider.getChoice().getAllowedTargets(), battle)
                            )
                    );
                }
            }
        });
        states.add(new StateAdapter() {
            private TargetChoiceProvider provider;

            @Override
            public String getKey() {
                return "selectTargets";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                provider = (TargetChoiceProvider) params.get("targetChoiceProvider");
            }

            @Override
            public void update(final long elapsedTime) {
                if (provider.isComplete()) {
                    battle.queueEvent(
                            new ActionEvent(
                                    combatant,
                                    actionType.createAction(combatant, provider.getTargets()),
                                    TICKS_REMAINING_IMMEDIATE
                            )
                    );
                    event.complete();
                }
            }
        });

        return new StateMachine(states, "initial");
    }
}
