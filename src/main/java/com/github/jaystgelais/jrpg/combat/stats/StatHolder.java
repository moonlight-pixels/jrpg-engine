package com.github.jaystgelais.jrpg.combat.stats;

public interface StatHolder {
    <T extends Stat> Stat getStat(Class<T> statClass) throws MissingStatException;

    default int getBaseStatValue(Class<? extends Stat> statClass) {
        return getStat(statClass).getBaseValue();
    }

    default int getStatValue(Class<? extends Stat> statClass) {
        return getStat(statClass).getValue();
    }
}
