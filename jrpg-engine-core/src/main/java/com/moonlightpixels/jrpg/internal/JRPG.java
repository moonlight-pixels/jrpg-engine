package com.moonlightpixels.jrpg.internal;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.combat.internal.CombatState;
import com.moonlightpixels.jrpg.frontend.internal.FrontEndState;
import com.moonlightpixels.jrpg.map.internal.MapState;

import javax.inject.Inject;

public final class JRPG {
    private final FrontEndState frontEndState;
    private final MapState mapState;
    private final CombatState combatState;
    private final StateMachine<JRPG, State<JRPG>> stateMachine;

    @Inject
    public JRPG(final FrontEndState frontEndState, final MapState mapState,
                final CombatState combatState, final State<JRPG> initialState) {
        this.frontEndState = frontEndState;
        this.mapState = mapState;
        this.combatState = combatState;
        this.stateMachine = new DefaultStateMachine<>(this, initialState);
    }

    void update() {
        stateMachine.update();
    }

    public void toLocation() {
        stateMachine.changeState(mapState);
    }

    public void toBattle() {
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
