package com.moonlightpixels.jrpg.random;

import com.badlogic.gdx.math.MathUtils;
import com.google.common.base.Preconditions;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OddmentTable<T> {
    private final List<OddmentTableRow<T>> rows;

    private OddmentTable(final List<OddmentTableRow<T>> rows) {
        this.rows = rows;
    }

    /**
     * Get random value from the table.
     *
     * @return value from table
     */
    public T getValue() {
        int selection = MathUtils.random(1, getTotalOddments());
        for (OddmentTableRow<T> row : rows) {
            if (selection <= row.oddment) {
                return row.value;
            }
            selection -= row.oddment;
        }

        throw new IllegalStateException("[PROGRAMMER ERROR] Random number generated out of bounds of OddmentTable");
    }

    private int getTotalOddments() {
        return rows.stream()
            .map(OddmentTableRow::getOddment)
            .reduce(Integer::sum)
            .orElse(0);
    }

    /**
     * Get a builder for an Oddment table of the type specified.
     *
     * @param type Type of value in table
     * @param <T> Type of value in table
     * @return Builder
     */
    public static <T> OddmentTableBuilder<T> builder(final Class<T> type) {
        return new OddmentTableBuilder<T>();
    }

    public static final class OddmentTableBuilder<T> {
        private ArrayList<OddmentTableRow<T>> rows = new ArrayList<>();

        /**
         * Add a row to the table.
         *
         * @param oddment share of total occurances for this table row
         * @param value value for this row
         * @return this Builder
         */
        public OddmentTableBuilder row(final int oddment, final T value) {
            Preconditions.checkArgument(oddment > 0, "Oddment must be greater than zero.");
            rows.add(new OddmentTableRow<T>(oddment, value));
            return this;
        }

        /**
         * Builds the new OddmentTable.
         *
         * @return OddmentTable
         */
        public OddmentTable<T> build() {
            Preconditions.checkState(!rows.isEmpty(), "OddmentTable must have at least one row.");
            return new OddmentTable<T>(Collections.unmodifiableList(rows));
        }
    }

    @Value
    private static final class OddmentTableRow<T> {
        private final int oddment;
        private final T value;

        private OddmentTableRow(final int oddment, final T value) {
            this.oddment = oddment;
            this.value = value;
        }
    }
}
