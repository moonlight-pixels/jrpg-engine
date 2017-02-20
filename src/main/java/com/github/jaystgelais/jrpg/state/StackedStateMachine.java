package com.github.jaystgelais.jrpg.state;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public final class StackedStateMachine extends StateMachine {
    private Stack<State> stateHistory = new Stack<>();

    public StackedStateMachine(final Set<State> states, final State initialState) {
        super(states, initialState);
    }

    public void exitCurrentState() {
        if (stateHistory.isEmpty()) {
            throw new IllegalStateException(
                    "Attempted to exitCurrentState() with no states saved in history to transition to."
            );
        }

        getCurrentState().onExit();
        setCurrentState(stateHistory.pop());
        getCurrentState().onEnter(new HashMap<>());
    }

    @Override
    protected void onChange(final State oldState, final State newState) {
        stateHistory.push(oldState);
    }
}