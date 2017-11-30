package com.github.jaystgelais.jrpg.combat.enemy;

import com.github.jaystgelais.jrpg.combat.Combatant;
import com.github.jaystgelais.jrpg.combat.stats.MaxHP;
import com.github.jaystgelais.jrpg.combat.stats.MaxMP;
import com.github.jaystgelais.jrpg.combat.stats.MissingStatException;
import com.github.jaystgelais.jrpg.combat.stats.Stat;

import java.util.HashMap;
import java.util.Map;

public class Enemy implements Combatant {
    private final String name;
    private int currentHp;
    private int currentMp;
    private final Map<Class<? extends Stat>, Stat> stats = new HashMap<>();
    private final int level;

    public Enemy(final String name, final int level, final Stat... stats) {
        this.name = name;
        this.level = level;
        for (Stat stat : stats) {
            this.stats.put(stat.getClass(), stat);
        }
        currentHp = getStatValue(MaxHP.class);
        currentMp = getStatValue(MaxMP.class);
    }

    public final String getName() {
        return name;
    }

    public final int getCurrentHp() {
        return Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final void setCurrentHp(final int currentHp) {
        this.currentHp = Math.min(currentHp, getStatValue(MaxHP.class));
    }

    public final int getCurrentMp() {
        return Math.min(currentMp, getStatValue(MaxMP.class));
    }

    public final void setCurrentMp(final int currentMp) {
        this.currentMp = Math.min(currentMp, getStatValue(MaxMP.class));
    }

    @Override
    public final int getLevel() {
        return level;
    }

    @Override
    public final <T extends Stat> Stat getStat(final Class<T> statClass) throws MissingStatException {
        if (!stats.containsKey(statClass)) {
            throw new MissingStatException(statClass);
        }

        return stats.get(statClass);
    }

    @Override
    public final void applyDamage(final int damage) {
        setCurrentHp(Math.max(getCurrentHp() - damage, 0));
    }
}
