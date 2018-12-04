package com.moonlightpixels.jrpg.player.equipment;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatAddition;
import com.moonlightpixels.jrpg.combat.stats.StatModifierHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMultiplier;
import com.moonlightpixels.jrpg.inventory.Item;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public final class Equipment implements Item, StatModifierHolder {
    private final Key key;
    private final EquipmentType type;
    private final String name;
    @Singular
    private final List<StatAddition> statAdditions;
    @Singular
    private final List<StatMultiplier> statMultipliers;

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

    public interface Key extends Item.Key { }
}
