package com.moonlightpixels.jrpg.combat.stats;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Optional;
import java.util.function.Function;

/**
 * A Stat is a property of a combat entity (players and enemies) required for the combat simulation. Stats come in two
 * types, {@link BaseStat}s and {@link CompositeStat}s.
 */
@SuperBuilder
@EqualsAndHashCode
public abstract class Stat {
    @Getter
    @NonNull
    private final Key key;

    @Getter
    @NonNull
    private final String name;

    @Getter
    @NonNull
    private final String shortName;

    private final Integer cap;

    /**
     * Returns the base value of this Stat, before applying modifiers.
     *
     * @param subject StatHolder containing the base value.
     * @return int value of stat
     */
    public abstract int getBaseValue(StatHolder subject);

    /**
     * Get the cap for this stat, if one exists.
     *
     * @return Optional Integer
     */
    public final Optional<Integer> getCap() {
        return Optional.ofNullable(cap);
    }

    /**
     * Returns the base value of this Stat, after applying modifiers.
     *
     * @param subject StatHolder containing the base value.
     * @return int value of stat
     */
    public final int getValue(final StatHolder subject) {
        final int modifiedValue = calculateModifiedValue(subject);
        return getCap().map(maxValue -> Math.min(maxValue, modifiedValue)).orElse(modifiedValue);
    }

    private int calculateModifiedValue(final StatHolder subject) {
        return subject.getStatModifiers(key).stream()
            .reduce(Function.identity(), Function::andThen)
            .apply(subject.getBaseValue(key));
    }

    public interface Key { }
}
