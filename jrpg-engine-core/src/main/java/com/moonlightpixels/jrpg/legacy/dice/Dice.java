package com.moonlightpixels.jrpg.legacy.dice;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jgelais on 12/27/16.
 */
public final class Dice {
    private List<Rollable> rolls = new LinkedList<>();

    public Dice add(final String rollDescription) {
        String[] numbers = rollDescription.split("[dD]");
        if (numbers.length == 1) {
            rolls.add(new FixedRoll(Integer.parseInt(numbers[0])));
        } else if (numbers.length == 2) {
            rolls.add(new DieRoll(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
        } else {
            throw new IllegalArgumentException(
                    "Input must either be a fixed number or a die roll specified as {rolls}d{sidesOfDie}"
            );
        }

        return this;
    }

    void add(final Rollable roll) {
        rolls.add(roll);
    }

    public int roll() {
        int result = 0;
        for (Rollable thisRoll : rolls) {
            result += thisRoll.roll();
        }

        return result;
    }

}
