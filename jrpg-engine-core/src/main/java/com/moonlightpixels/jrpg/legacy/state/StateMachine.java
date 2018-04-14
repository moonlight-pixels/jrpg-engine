package com.moonlightpixels.jrpg.legacy.state;

import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.graphics.Renderable;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.input.InputService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StateMachine implements Renderable, InputHandler {
    private Map<String, State> states = new HashMap<>();
    private final String initialState;
    private State currentState;

    public StateMachine(final Set<? extends State> states, final String initialState) {
        if (states == null || states.isEmpty()) {
            throw new IllegalArgumentException("states MUST NOT be null and MUST be a non-empty Set.");
        }
        if (initialState == null) {
            throw new IllegalArgumentException("initialState MUST NOT be null.");
        }

        for (State state : states) {
            this.states.put(state.getKey(), state);
        }

        if (!this.states.containsKey(initialState)) {
            throw new IllegalArgumentException("Unkown state " + initialState);
        }
        this.initialState = initialState;
    }

    public final State getCurrentState() {
        if (currentState == null) {
            change(initialState);
        }
        return currentState;
    }

    protected final void setCurrentState(final State state) {
        currentState = state;
    }

    protected final String getInitialState() {
        return initialState;
    }

    public final void update(final long elapsedTime) {
        getCurrentState().update(elapsedTime);
    }

    public final void render(final GraphicsService graphicsService) {
        getCurrentState().render(graphicsService);
    }

    public final void change(final String stateKey) {
        change(stateKey, Collections.emptyMap());
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

        if (currentState != null) {
            currentState.onExit();
            State previousState = currentState;
            currentState = states.get(stateKey);
            currentState.onEnter(params);
            onChange(previousState, currentState);
        } else {
            currentState = states.get(stateKey);
            currentState.onEnter(params);
        }
    }

    public final boolean hasState(final String stateKey) {
        return states.containsKey(stateKey);
    }

    protected void onChange(final State oldState, final State newState) { }

    @Override
    public final void dispose() {
        states.values().forEach(state -> state.dispose());
    }

    @Override
    public final void handleInput(final InputService inputService) {
        getCurrentState().handleInput(inputService);
    }
}
