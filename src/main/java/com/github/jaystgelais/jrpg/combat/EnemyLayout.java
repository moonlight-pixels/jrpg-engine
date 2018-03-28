package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.enemy.Enemy;
import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

public final class EnemyLayout {
    private final List<Coordinate2D> locations;

    public EnemyLayout(final Coordinate2D ...locations) {
        this.locations = Arrays.asList(locations);
    }

    public int slots() {
        return locations.size();
    }

    public void renderEnemies(final GraphicsService graphicsService, final List<Enemy> enemies) {
        Preconditions.checkArgument(enemies.size() <= slots());
        for (int index = 0; index < enemies.size(); index++) {
            final Enemy enemy = enemies.get(index);
            if (enemy.isAlive()) {
                final Coordinate2D location = locations.get(index);
                graphicsService.drawSprite(
                        enemy.getImage().getTexture(graphicsService.getAssetManager()),
                        location.getX(),
                        location.getY()
                );
            }
        }
    }
}
