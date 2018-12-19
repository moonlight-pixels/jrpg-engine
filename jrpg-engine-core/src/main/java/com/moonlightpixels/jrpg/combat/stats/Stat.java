package com.moonlightpixels.jrpg.combat.stats;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;

/**
 * A Stat is a property of a combat entity (players and enemies) required for the combat simulation. Stats come in two
 * types, {@link BaseStat}s and {@link CompositeStat}s.
 */
@Data
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

    private final StatCap cap;

    private final Integer minValue;

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
     * @return Optional StatCap
     */
    public final Optional<StatCap> getCap() {
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
        return getCap()
            .filter(cap -> cap.appliesTo(subject))
            .map(cap -> Math.min(cap.getCap(), modifiedValue))
            .orElse(modifiedValue);
    }

    private int calculateModifiedValue(final StatHolder subject) {
        return new LinkedList<Function<Integer, Integer>>(subject.getStatModifiers(key)).stream()
            .reduce(Function.identity(), Function::andThen)
            .apply(getBaseValue(subject));
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public interface Key { }
}
