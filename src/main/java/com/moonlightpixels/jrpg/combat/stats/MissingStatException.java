package com.moonlightpixels.jrpg.combat.stats;

public class MissingStatException extends RuntimeException {
    private final Class<? extends Stat> statClass;

    public MissingStatException(final Class<? extends Stat> statClass) {
        this.statClass = statClass;
    }

    public MissingStatException(final Class<? extends Stat> statClass, final String message) {
        super(message);
        this.statClass = statClass;
    }
}
