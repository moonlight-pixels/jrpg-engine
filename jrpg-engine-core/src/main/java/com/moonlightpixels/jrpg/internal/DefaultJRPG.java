package com.moonlightpixels.jrpg.internal;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.combat.CombatState;
import com.moonlightpixels.jrpg.combat.Encounter;
import com.moonlightpixels.jrpg.frontend.internal.FrontEndState;
import com.moonlightpixels.jrpg.input.InputSystem;
import com.moonlightpixels.jrpg.input.KeyboardMapping;
import com.moonlightpixels.jrpg.internal.plugin.PluginSystem;
import com.moonlightpixels.jrpg.map.internal.MapState;

import javax.inject.Inject;
import javax.inject.Named;

import static com.moonlightpixels.jrpg.internal.inject.GameModule.INITIAL_STATE;

public final class DefaultJRPG implements JRPG {
    private final FrontEndState frontEndState;
    private final MapState mapState;
    private final CombatState combatState;
    private final StateMachine<JRPG, GameMode> stateMachine;
    private final InputSystem inputSystem;
    private final PluginSystem pluginSystem;

    @Inject
    public DefaultJRPG(final FrontEndState frontEndState,
                       final MapState mapState,
                       final CombatState combatState,
                       @Named(INITIAL_STATE) final GameMode initialState,
                       final InputSystem inputSystem,
                       final PluginSystem pluginSystem) {
        this.frontEndState = frontEndState;
        this.mapState = mapState;
        this.combatState = combatState;
        this.stateMachine = new DefaultStateMachine<>(this, initialState);
        this.inputSystem = inputSystem;
        this.pluginSystem = pluginSystem;
    }

    @Override
    public void init() {
        pluginSystem.loadPlugins();
        inputSystem.useKeyboard(KeyboardMapping.DEFAULT);
        stateMachine.getCurrentState().enter(this);
    }

    public void update() {
        stateMachine.getCurrentState().setInputScheme(inputSystem.getInputScheme());
        inputSystem.passEventsToHandler(stateMachine.getCurrentState());
        pluginSystem.preRender();
        stateMachine.update();
        pluginSystem.postRender();
    }

    public void toMap() {
        stateMachine.changeState(mapState);
    }

    public void toBattle(final Encounter encounter) {
        stateMachine.changeState(combatState);
    }

    public void toMainMenu() {
        stateMachine.changeState(frontEndState);
    }

    public void exitBattle() {
        Preconditions.checkState(stateMachine.getCurrentState() == combatState);
        if (!stateMachine.revertToPreviousState()) {
            throw new IllegalStateException("Exiting battle, but no previous state found to exit to.");
        }
    }
}
