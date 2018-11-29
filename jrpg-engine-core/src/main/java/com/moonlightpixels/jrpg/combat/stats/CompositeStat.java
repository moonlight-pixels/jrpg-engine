package com.moonlightpixels.jrpg.combat.stats;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public final class CompositeStat extends Stat {
    private final List<Stat> inputs;
    private final Function<Map<Stat.Key, Integer>, Integer> statFunction;

    @Builder
    private CompositeStat(final Key key,
                          final String name,
                          final String shortName,
                          final Integer cap,
                          @Singular final List<Stat> inputs,
                          @NonNull final Function<Map<Key, Integer>, Integer> statFunction) {
        super(key, name, shortName, cap);
        this.inputs = inputs;
        this.statFunction = statFunction;
    }

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
