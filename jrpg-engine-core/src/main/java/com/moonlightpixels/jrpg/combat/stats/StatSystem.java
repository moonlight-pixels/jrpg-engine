package com.moonlightpixels.jrpg.combat.stats;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Singular;

import java.util.Optional;
import java.util.Set;

@Builder
public final class StatSystem {
    @Singular
    private final Set<Stat> stats;

    private StatSystem(final Set<Stat>  stats) {
        for (Stat.Key key : RequiredStats.values()) {
            Preconditions.checkArgument(
                findByKey(stats, key).isPresent(),
                String.format("[%s] is a required Stat", key)
            );
        }
        this.stats = stats;
    }

    /**
     * Returns the registered stat with the given Key.
     *
     * @param key Key identifying stat
     * @return Stat
     */
    public Stat getStat(final Stat.Key key) {
        return findByKey(stats, key)
            .orElseThrow(() -> new IllegalArgumentException(String.format("No Stat with key {%s} found", key)));
    }

    private static Optional<Stat> findByKey(final Set<Stat> stats, final Stat.Key key) {
        return stats.stream()
            .filter(stat -> stat.getKey().equals(key))
            .findFirst();
    }

}
