package com.github.jaystgelais.jrpg.combat;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.ui.Panel;
import com.github.jaystgelais.jrpg.ui.UiStyle;

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
        states.add(createActiveState());
        states.add(createVictoryState());
        states.add(createGameOverState());
        return new StateMachine(states, "active");
    }

    private State createActiveState() {
        return new StateAdapter() {
            @Override
            public String getKey() {
                return "active";
            }

            @Override
            public void update(final long elapsedTime) {
                if (battle.isGameOver()) {
                    stateMachine.change("gameover");
                } else if (battle.isVictory()) {
                    stateMachine.change("victory");
                } else {
                    battle.update(elapsedTime);
                }
            }

            @Override
            public void handleInput(final InputService inputService) {
                battleSystem.handleInput(inputService);
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                battleSystem.render(graphicsService);
            }

            @Override
            public void onExit() {
                Game.getInstance().getUserInterface().clear();
            }
        };
    }

    private State createVictoryState() {
        return new StateAdapter() {
            @Override
            public String getKey() {
                return "victory";
            }
        };
    }

    private State createGameOverState() {
        return new StateAdapter() {
            @Override
            public String getKey() {
                return "gameover";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                Label label = new Label("Game Over", UiStyle.get(Label.LabelStyle.class));
                Panel<Label> panel = new Panel<Label>(label, UiStyle.get(Panel.PanelStyle.class));
                Game.getInstance().getUserInterface().add(panel);
            }
        };
    }
}
