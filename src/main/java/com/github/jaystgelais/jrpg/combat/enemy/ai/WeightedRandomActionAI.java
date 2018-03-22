package com.github.jaystgelais.jrpg.combat.enemy.ai;

import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.util.OddmentTable;

public final class WeightedRandomActionAI extends ActionSelectingAI {
    private final OddmentTable<EnemyAction> table;

    private WeightedRandomActionAI(final OddmentTable<EnemyAction> table) {
        this.table = table;
    }

    @Override
    protected EnemyAction getEnemyAction(final Battle battle) {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final OddmentTable<EnemyAction> table = new OddmentTable<>();

        private Builder() { }

        public Builder add(final int weight, final EnemyAction action) {
            table.addRow(weight, action);
            return this;
        }

        public WeightedRandomActionAI build() {
            return new WeightedRandomActionAI(table);
        }
    }
}
