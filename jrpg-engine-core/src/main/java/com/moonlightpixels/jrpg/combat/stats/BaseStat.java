package com.moonlightpixels.jrpg.combat.stats;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public final class BaseStat extends Stat {
    @Builder
    private BaseStat(final Key key,
                     final String name,
                     final String shortName,
                     final Integer cap) {
        super(key, name, shortName, cap);
    }

    @Override
    public int getBaseValue(final StatHolder subject) {
        return subject.getBaseValue(getKey());
    }
}
