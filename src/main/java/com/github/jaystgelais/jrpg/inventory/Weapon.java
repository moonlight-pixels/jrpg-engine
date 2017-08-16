package com.github.jaystgelais.jrpg.inventory;

import com.github.jaystgelais.jrpg.combat.stats.AttackPower;
import com.github.jaystgelais.jrpg.combat.stats.Stat;
import com.github.jaystgelais.jrpg.combat.stats.StatHolder;
import com.github.jaystgelais.jrpg.combat.stats.StatModifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Weapon extends Equipment {

    public Weapon(final String name, final int defaultPrice, final Stat... stats) {
        this(name, defaultPrice, new HashSet<StatModifier>(), stats);
    }

    public Weapon(final String name, final int defaultPrice,
                  final Collection<StatModifier> statModifiers, final Stat... stats) {
        super(name, defaultPrice, statModifiers, stats);
    }

    @Override
    public final EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HAND;
    }

    @Override
    public final Collection<Class<? extends Stat>> getStatTypes() {
        return Stream.concat(
                Stream.of(AttackPower.class),
                getAdditionalStatTypes().stream()
        ).collect(Collectors.toSet());
    }

    protected abstract Collection<Class<? extends Stat>> getAdditionalStatTypes();

    public abstract int getRawDamage(StatHolder attacker);
}