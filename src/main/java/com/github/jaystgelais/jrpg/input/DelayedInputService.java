package com.github.jaystgelais.jrpg.input;

import java.util.HashMap;
import java.util.Map;

public final class DelayedInputService implements InputService {
    private static Map<Inputs, DelayedInput> delayedInputs;
    static {
        delayedInputs = new HashMap<>();
        for (Inputs input : Inputs.values()) {
            delayedInputs.put(input, new DelayedInput(input));
        }
    }

    private final InputService inputService;

    public DelayedInputService(final InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public boolean isPressed(final Inputs input) {
        return delayedInputs.get(input).isPressed(inputService);
    }
}
