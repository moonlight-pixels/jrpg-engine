package com.github.jaystgelais.jrpg.state;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StateMachine {
    private Map<String, State> states = new HashMap<>();
    private State currentState;

    public StateMachine(final Set<State> states, final State initialState) {
        if (states == null || states.isEmpty()) {
            throw new IllegalArgumentException("states MUST NOT be null and MUST be a non-empty Set.");
        }
        if (initialState == null) {
            throw new IllegalArgumentException("initialState MUST NOT be null.");
        }

        for (State state : states) {
            this.states.put(state.getKey(), state);
        }
        currentState = initialState;
    }

    protected final State getCurrentState() {
        return currentState;
    }

    protected final void setCurrentState(final State state) {
        currentState = state;
    }

    public final void update(final long elapsedTime) {
        currentState.update(elapsedTime);
    }

    public final void render(final GraphicsService graphicsService) {
        currentState.render(graphicsService);
    }

    public final void change(final String stateKey) {
        change(stateKey, new HashMap<>());
    }

    public final void change(final String stateKey, final Map<String, Object> params) {
        if (!states.containsKey(stateKey)) {
            throw new IllegalArgumentException(String.format("Unknown State %s", stateKey));
        }
        if (params == null) {
            throw new IllegalArgumentException(
                    "params MUST NOT be null. Use change(String stateKey) instead if no params are given."
            );
        }

        currentState.onExit();
        State previousState = currentState;
        currentState = states.get(stateKey);
        currentState.onEnter(params);
        onChange(previousState, currentState);
    }

    protected void onChange(final State oldState, final State newState) { }
}
