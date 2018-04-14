package com.moonlightpixels.jrpg.legacy.util;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.base.Preconditions;

import java.util.LinkedList;
import java.util.List;

public final class OddmentTable<T> {
    private final List<OddmentTableRow> rows = new LinkedList<>();
    private int totalOddments = 0;

    public void addRow(final int oddment, final T value) {
        Preconditions.checkArgument(oddment > 0, "Oddments must be positive integers");
        Preconditions.checkNotNull(value, "Value must not be null");
        rows.add(new OddmentTableRow(oddment, value));
        totalOddments += oddment;
    }

    public T getValue() {
        int selection = MathUtils.random(1, totalOddments);
        for (OddmentTableRow row : rows) {
            if (selection <= row.oddment) {
                return row.value;
            }
            selection -= row.oddment;
        }

        throw new IllegalStateException("[PROGRAMMER ERROR] Random number generated out of bounds of OddmentTable");
    }

    public boolean isEmpty() {
        return rows.isEmpty();
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
