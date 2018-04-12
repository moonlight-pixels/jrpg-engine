package com.moonlightpixels.jrpg.input;

import java.time.Clock;

public final class DelayedInput {
    public static final long DEFAULT_DELAY_MS = 300L;

    private final Clock clock;
    private final long delayMs;
    private final Inputs input;
    private Long lastPressed;

    public DelayedInput(final Inputs input) {
        this(input, DEFAULT_DELAY_MS);
    }

    public DelayedInput(final Inputs input, final long delayMs) {
        this(input, delayMs, Clock.systemUTC());
    }

    public DelayedInput(final Inputs input, final long delayMs, final Clock clock) {
        this.input = input;
        this.delayMs = delayMs;
        this.clock = clock;
        delay();
    }

    public boolean isPressed(final InputService inputService) {
        long now = clock.millis();
        if (inputService.isPressed(input) && now - lastPressed > delayMs) {
            delay();
            return true;
        }

        return false;
    }

    public void delay() {
        lastPressed = clock.millis();
    }
}
