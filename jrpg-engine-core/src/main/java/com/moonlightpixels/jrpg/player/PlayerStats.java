package com.moonlightpixels.jrpg.player;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.combat.stats.RequiredStats;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatAddition;
import com.moonlightpixels.jrpg.combat.stats.StatHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMeter;
import com.moonlightpixels.jrpg.combat.stats.StatMultiplier;
import com.moonlightpixels.jrpg.combat.stats.StatSystem;
import com.moonlightpixels.jrpg.player.equipment.EquipmentSet;
import com.moonlightpixels.jrpg.player.equipment.EquipmentSlot;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
final class PlayerStats implements StatHolder {
    private final StatSystem statSystem;
    private final Map<Stat.Key, Integer> baseValues = new HashMap<>();
    private final EquipmentSet equipmentSet;
    private final StatMeter hitPoints;
    private final Map<Stat.Key, StatMeter> meters = new HashMap<>();

    PlayerStats(final StatSystem statSystem,
                final List<EquipmentSlot> equipmentSlots,
                final Map<Stat.Key, Integer> baseValues) {
        this.statSystem = statSystem;
        this.equipmentSet = new EquipmentSet(equipmentSlots);
        for (Map.Entry<Stat.Key, Integer> entry : baseValues.entrySet()) {
            this.baseValues.put(entry.getKey(), entry.getValue());
        }
        hitPoints = new StatMeter(this, statSystem.getStat(RequiredStats.MaxHP));
    }

    public EquipmentSet getEquipmentSet() {
        return equipmentSet;
    }

    public StatMeter getHitPoints() {
        return hitPoints;
    }

    public StatMeter getMeter(final Stat.Key maxValueKey) {
        return meters.get(maxValueKey);
    }

    public void addMeter(final Stat.Key maxValueKey) {
        meters.put(maxValueKey, new StatMeter(this, statSystem.getStat(maxValueKey)));
    }

    public int getStatValue(final Stat.Key key) {
        return statSystem.getStat(key).getValue(this);
    }

    @Override
    public int getBaseValue(final Stat.Key key) {
        Preconditions.checkArgument(
            baseValues.containsKey(key),
            String.format("This player does not have a value for stat [%s]", key)
        );
        return baseValues.get(key);
    }

    @Override
    public Type getHolderType() {
        return Type.Player;
    }

    public void setBaseValue(final Stat.Key key, final int value) {
        baseValues.put(key, value);
    }

    public List<EquipmentSet.SlotAssignement> getEquipmentSlots() {
        return equipmentSet.getEquipmentSlots();
    }

    @Override
    public List<StatAddition> getStatAdditions(final Stat.Key stat) {
        return equipmentSet.getEquipmentSlots().stream()
            .map(EquipmentSet.SlotAssignement::getEquipment)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(equipment -> equipment.getStatAdditions(stat))
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    @Override
    public List<StatMultiplier> getStatMultipliers(final Stat.Key stat) {
        return equipmentSet.getEquipmentSlots().stream()
            .map(EquipmentSet.SlotAssignement::getEquipment)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(equipment -> equipment.getStatMultipliers(stat))
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
