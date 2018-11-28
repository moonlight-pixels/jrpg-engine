package com.moonlightpixels.jrpg.combat.stats;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder
public final class CompositeStat extends Stat {
    private final List<Stat> inputs;
    private final Function<Map<Stat.Key, Integer>, Integer> statFunction;

    @Override
    public int getBaseValue(final StatHolder subject) {
        return statFunction.apply(
            inputs.stream()
                .collect(
                    Collectors.toMap(Stat::getKey,
                        stat -> stat.getValue(subject))
                )
        );
    }
}
