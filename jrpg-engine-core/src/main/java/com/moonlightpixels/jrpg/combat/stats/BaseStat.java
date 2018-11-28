package com.moonlightpixels.jrpg.combat.stats;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public final class BaseStat extends Stat {
    @Override
    public int getBaseValue(final StatHolder subject) {
        return subject.getBaseValue(getKey());
    }
}
