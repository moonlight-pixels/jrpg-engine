package com.moonlightpixels.jrpg.player;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatAddition;
import com.moonlightpixels.jrpg.combat.stats.StatHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMultiplier;
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
    private final Map<Stat.Key, Integer> baseValues = new HashMap<>();
    private final EquipmentSet equipmentSet;

    PlayerStats(final List<EquipmentSlot> equipmentSlots) {
        this.equipmentSet = new EquipmentSet(equipmentSlots);
    }

    @Override
    public int getBaseValue(final Stat.Key key) {
        Preconditions.checkArgument(
            baseValues.containsKey(key),
            String.format("This player does not have a value for stat [%s]", key)
        );
        return baseValues.get(key);
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
