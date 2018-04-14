package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.combat.enemy.Enemy;
import com.moonlightpixels.jrpg.legacy.combat.enemy.EnemyProvider;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.Collectors;

public final class Encounter {
    private final EnemyLayout layout;
    private final List<EnemyProvider> enemies;

    public Encounter(final EnemyLayout layout, final List<EnemyProvider> enemies) {
        Preconditions.checkArgument(enemies.size() <= layout.slots());
        this.layout = layout;
        this.enemies = enemies;
    }

    public EnemyLayout getLayout() {
        return layout;
    }

    public List<Enemy> getEnemies() {
        return enemies.stream().map(EnemyProvider::get).collect(Collectors.toList());
    }
}
