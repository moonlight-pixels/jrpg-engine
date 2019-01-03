package com.moonlightpixels.jrpg.combat;

import lombok.Data;

import java.util.List;

@Data
public final class Encounter {
    private final List<Enemy> enemies;

    /**
     * Constructs new encounter.
     *
     * @param enemies Enemies in the encounter
     */
    public Encounter(final List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
