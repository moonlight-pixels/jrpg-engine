package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class CombatMode extends GameMode {
    private final BattleSystem battleSystem;
    private final StateMachine stateMachine;
    private Battle battle;

    public CombatMode(final BattleSystem battleSystem) {
        this.battleSystem = battleSystem;
        stateMachine = initStateMachine();
    }

    @Override
    public String getKey() {
        return "combatMode";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        final Encounter encounter = (Encounter) params.get("encounter");
        battle = new Battle(battleSystem, GameState.getParty(), encounter.getEnemies());
        battleSystem.registerEncounter(encounter);
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "active";
            }

            @Override
            public void update(final long elapsedTime) {
                battle.update(elapsedTime);
            }

            @Override
            public void handleInput(final InputService inputService) {
                battleSystem.handleInput(inputService);
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                battleSystem.render(graphicsService);
            }
        });
        return new StateMachine(states, "active");
    }
}
