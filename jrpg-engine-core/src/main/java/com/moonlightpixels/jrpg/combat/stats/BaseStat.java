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
                     final StatCap cap,
                     final Integer minValue) {
        super(key, name, shortName, cap, minValue);
    }

    @Override
    public int getBaseValue(final StatHolder subject) {
        return subject.getBaseValue(getKey());
    }

    public static final class BaseStatBuilder {
        /**
         * Adds a stat cap that applies to all holder types.
         *
         * @param cap capped Value
         * @return this builder
         */
        public BaseStatBuilder cap(final int cap) {
            this.cap = new StatCap(cap, StatHolder.Type.Player, StatHolder.Type.Enemy);
            return this;
        }

        /**
         * Adds a stat cap.
         *
         * @param cap capped Value
         * @return this builder
         */
        public BaseStatBuilder cap(final StatCap cap) {
            this.cap = cap;
            return this;
        }
    }
}
