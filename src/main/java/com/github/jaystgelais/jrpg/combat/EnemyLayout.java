package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.enemy.Enemy;
import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EnemyLayout {
    private final List<Coordinate2D> locations;

    public EnemyLayout(final Coordinate2D ...locations) {
        this.locations = Arrays.asList(locations);
    }

    public int slots() {
        return locations.size();
    }

    public Map<Enemy, Coordinate2D> getEnemyPositions(final List<Enemy> enemies) {
        Preconditions.checkArgument(enemies.size() <= slots());

        final Map<Enemy, Coordinate2D> enemyPositions = new HashMap<>();
        for (int index = 0; index < enemies.size(); index++) {
            final Enemy enemy = enemies.get(index);
            final Coordinate2D location = locations.get(index);
            enemyPositions.put(enemy, location);
        }

        return enemyPositions;
    }
}
