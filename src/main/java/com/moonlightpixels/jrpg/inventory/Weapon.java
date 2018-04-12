package com.moonlightpixels.jrpg.inventory;

import com.moonlightpixels.jrpg.combat.stats.AttackPower;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatHolder;
import com.moonlightpixels.jrpg.combat.stats.StatModifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Weapon extends Equipment {

    public Weapon(final String id, final String name, final String description, final int sellBackPrice,
                  final Stat... stats) {
        super(id, name, description, sellBackPrice, new HashSet<>(), stats);
    }

    public Weapon(final String id, final String name, final String description, final int sellBackPrice,
                  final Collection<StatModifier> statModifiers, final Stat... stats) {
        super(id, name, description, sellBackPrice, statModifiers, stats);
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
