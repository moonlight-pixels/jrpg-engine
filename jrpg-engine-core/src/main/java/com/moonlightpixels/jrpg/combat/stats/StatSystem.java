package com.moonlightpixels.jrpg.combat.stats;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class StatSystem {
    private final Set<Stat> stats = new HashSet<>();

    /**
     * Registers a Stat definition.
     *
     * @param stat Stat to register
     */
    public void addStat(final Stat stat) {
        stats.add(stat);
    }

    /**
     * Returns the registered stat with the given Key.
     *
     * @param key Key identifying stat
     * @return Stat
     */
    public Stat getStat(final Stat.Key key) {
        return findByKey(key)
            .orElseThrow(() -> new IllegalArgumentException(String.format("No Stat with key {%s} found", key)));
    }

    /**
     * Returns true if all RequiredStats have been configured.
     *
     * @return true if all RequiredStats have been configured
     */
    public boolean isValid() {
        for (Stat.Key key : RequiredStats.values()) {
            if (!findByKey(key).isPresent()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns Set of registered stats.
     *
     * @return stats
     */
    public Set<Stat> getStats() {
        return Collections.unmodifiableSet(stats);
    }

    private Optional<Stat> findByKey(final Stat.Key key) {
        return stats.stream()
            .filter(stat -> stat.getKey().equals(key))
            .findFirst();
    }

}
