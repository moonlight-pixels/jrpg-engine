package com.github.jaystgelais.jrpg.dice;

import com.badlogic.gdx.math.MathUtils;

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
        return MathUtils.random(1, sides);
    }

    @Override
    public String toString() {
        return String.format("%dd%d", rolls, sides);
    }
}
