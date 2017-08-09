package com.github.jaystgelais.jrpg.combat.stats;

public interface StatHolder {
    <T extends Stat> T getStat(Class<T> statClass);

    default int getBaseStatValue(Class<? extends Stat> statClass) {
        return getStat(statClass).getBaseValue();
    }

    default int getModifiedStatValue(Class<? extends Stat> statClass) {
        return getStat(statClass).getModifiedValue();
    }
}
