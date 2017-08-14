package com.github.jaystgelais.jrpg.inventory;

import com.github.jaystgelais.jrpg.combat.stats.Stat;
import com.github.jaystgelais.jrpg.combat.stats.StatModifier;
import com.github.jaystgelais.jrpg.party.CharacterClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Equipment {
    private final String name;
    private final int sellBackPrice;
    private final Set<StatModifier> statModifiers = new HashSet<>();
    private final Map<Class<? extends Stat>, Stat> stats = new HashMap<>();

    public Equipment(final String name, final int defaultPrice,
                        final Collection<StatModifier> statModifiers, final Stat... stats) {
        this.name = name;
        this.sellBackPrice = defaultPrice;
        this.statModifiers.addAll(statModifiers);
        for (Stat stat : stats) {
            if (!getStatTypes().contains(stat.getClass())) {
                throw new IllegalArgumentException(
                        String.format("Equipment Type [%s] does not support stat type %s", getClass(), stat.getClass())
                );
            }
            this.stats.put(stat.getClass(), stat);
            for (Class<? extends Stat> statType : getStatTypes()) {
                if (!this.stats.keySet().contains(statType)) {
                    throw new IllegalArgumentException(
                            String.format("Equipment Type [%s] requires stat type %s", getClass(), statType)
                    );
                }
            }
        }
    }

    public abstract EquipmentSlot getEquipmentSlot();

    public abstract Collection<Class<? extends Stat>> getStatTypes();

    public abstract boolean canEquip(CharacterClass characterClass);

    public final String getName() {
        return name;
    }

    public final int getSellBackPrice() {
        return sellBackPrice;
    }
}
