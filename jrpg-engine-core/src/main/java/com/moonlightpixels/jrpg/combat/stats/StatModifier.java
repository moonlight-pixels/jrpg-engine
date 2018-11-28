package com.moonlightpixels.jrpg.combat.stats;

import lombok.Data;

import java.util.function.Function;

@Data
abstract class StatModifier implements Function<Integer, Integer> {
    private final Stat.Key stat;
}
