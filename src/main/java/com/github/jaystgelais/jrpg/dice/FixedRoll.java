package com.github.jaystgelais.jrpg.dice;

/**
 * Created by jgelais on 12/27/16.
 */
final class FixedRoll implements Rollable {
    private final int value;

    public FixedRoll(final int value) {
        this.value = value;
    }

    @Override
    public int roll() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
