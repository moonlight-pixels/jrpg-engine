package com.github.jaystgelais.jrpg.input;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jgelais on 2/19/17.
 */
public abstract class AbstractInputService implements InputService {
    private final Clock clock;
    private final long inputDelayMs;
    private final Map<Inputs, Long> inputLastRead = new HashMap<>();

    public AbstractInputService(final long inputDelayMs) {
        this(inputDelayMs, Clock.systemUTC());
    }

    AbstractInputService(final long inputDelayMs, final Clock clock) {
        this.clock = clock;
        this.inputDelayMs = inputDelayMs;
    }

    @Override
    public final boolean isPressed(final Inputs input) {
        long currentTime = clock.millis();
        if (inputLastRead.containsKey(input)) {
            if (currentTime - inputLastRead.get(input) < inputDelayMs) {
                return false;
            }
            inputLastRead.remove(input);
        }
        boolean isPressed = checkForInput(input);
        if (isPressed) {
            inputLastRead.put(input, currentTime);
        }
        return isPressed;
    }

    protected abstract boolean checkForInput(Inputs inputs);
}
