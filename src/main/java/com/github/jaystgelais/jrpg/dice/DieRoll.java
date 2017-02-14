package com.github.jaystgelais.jrpg.dice;

import java.util.concurrent.ThreadLocalRandom;

final class DieRoll implements Rollable {
    private final int rolls;
    private final int sides;

    DieRoll(final int rolls, final int sides) {
        this.rolls = rolls;
        this.sides = sides;
    }

    public int roll() {
        int result = 0;
        for (int x = 0; x < rolls; x++) {
            result += rollDie();
        }

        return result;
    }

    private int rollDie() {
        return ThreadLocalRandom.current().nextInt(1, sides + 1);
    }

    @Override
    public String toString() {
        return String.format("%dd%d", rolls, sides);
    }
}
