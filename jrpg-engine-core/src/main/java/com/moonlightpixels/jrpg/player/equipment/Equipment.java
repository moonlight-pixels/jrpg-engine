package com.moonlightpixels.jrpg.player.equipment;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatAddition;
import com.moonlightpixels.jrpg.combat.stats.StatModifierHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMultiplier;
import com.moonlightpixels.jrpg.inventory.Item;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public final class Equipment implements Item, StatModifierHolder {
    private final Key key;
    private final EquipmentType type;
    private final String name;
    private final List<StatAddition> statAdditions;
    private final List<StatMultiplier> statMultipliers;

    @Builder
    private Equipment(final Key key,
                     final EquipmentType type,
                     final String name,
                     final List<StatAddition> statAdditions,
                     final List<StatMultiplier> statMultipliers) {
        this.key = key;
        this.type = type;
        this.name = name;
        this.statAdditions = statAdditions;
        this.statMultipliers = statMultipliers;
    }

    @Override
    public List<StatAddition> getStatAdditions(final Stat.Key stat) {
        return statAdditions.stream()
            .filter(statAddition -> statAddition.getStat().equals(stat))
            .collect(Collectors.toList());
    }

    @Override
    public List<StatMultiplier> getStatMultipliers(final Stat.Key stat) {
        return statMultipliers.stream()
            .filter(statAdditon -> statAdditon.getStat().equals(stat))
            .collect(Collectors.toList());
    }

    public static final class EquipmentBuilder {
        private List<StatAddition> statAdditions = new ArrayList<>();
        private List<StatMultiplier> statMultipliers = new ArrayList<>();

        /**
         * Add a stat bonus/penalty to this piece of equipment.
         *
         * @param stat Stat to modify
         * @param addition bonus or penalty (negative value)
         * @return this EquipmentBuilder
         */
        public EquipmentBuilder statAddition(final Stat.Key stat, final int addition) {
            statAdditions.add(new StatAddition(stat, addition));
            return this;
        }

        /**
         * Add a stat bonus/penalty to this piece of equipment.
         *
         * @param stat Stat to modify
         * @param muliplier bonus (greater than 1.0) or penalty (less than 1.0)
         * @return this EquipmentBuilder
         */
        public EquipmentBuilder statMultipler(final Stat.Key stat, final float muliplier) {
            statMultipliers.add(new StatMultiplier(stat, muliplier));
            return this;
        }
    }

    public interface Key extends Item.Key { }
}
