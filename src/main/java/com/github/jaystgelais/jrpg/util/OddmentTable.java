package com.github.jaystgelais.jrpg.util;

import com.github.jaystgelais.jrpg.dice.Dice;

import java.util.LinkedList;
import java.util.List;

public final class OddmentTable<T> {
    private final List<OddmentTableRow> rows = new LinkedList<>();
    private int totalOddments = 0;

    public void addRow(final int oddment, final T value) {
        if (oddment < 1) {
            throw new IllegalArgumentException("Oddments must eb positive integers");
        }
        rows.add(new OddmentTableRow(oddment, value));
        totalOddments += oddment;
    }

    public T getValue() {
        int selection = new Dice().add("1d" + totalOddments).roll();
        for (OddmentTableRow row : rows) {
            if (selection <= row.oddment) {
                return row.value;
            }
            selection -= row.oddment;
        }

        return null;
    }

    private final class OddmentTableRow {
        private final int oddment;
        private final T value;

        private OddmentTableRow(final int oddment, final T value) {
            this.oddment = oddment;
            this.value = value;
        }
    }
}
