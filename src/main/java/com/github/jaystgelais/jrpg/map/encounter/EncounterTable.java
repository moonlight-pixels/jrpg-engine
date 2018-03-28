package com.github.jaystgelais.jrpg.map.encounter;

import com.github.jaystgelais.jrpg.combat.Encounter;
import com.github.jaystgelais.jrpg.util.OddmentTable;
import com.google.common.base.Preconditions;

import java.util.Optional;

public abstract class EncounterTable {
    private final OddmentTable<Encounter> table = new OddmentTable<>();
    private int stepsUntilNextEncounter = calculateStepsUntilNextEncounter();

    protected abstract int calculateStepsUntilNextEncounter();

    public final void addEncounter(final int weight, final Encounter encounter) {
        table.addRow(weight, encounter);
    }

    public final Optional<Encounter> registerStepAndCheckForEncounter() {
        Preconditions.checkArgument(!table.isEmpty());
        stepsUntilNextEncounter--;
        if (stepsUntilNextEncounter < 1) {
            stepsUntilNextEncounter = calculateStepsUntilNextEncounter();
            return Optional.of(table.getValue());
        }

        return Optional.empty();
    }
}
