package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.enemy.Enemy;

import java.util.List;

public final class Encounter {
    private final List<Enemy> enemies;

    public Encounter(final List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
