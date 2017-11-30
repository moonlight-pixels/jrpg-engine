package com.github.jaystgelais.jrpg.combat.render;

import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.github.jaystgelais.jrpg.ui.Container;

import java.util.Arrays;
import java.util.List;

public final class EnemyLayoutDefinition {
    private final List<Coordinate2D> enemyLocations;

    public EnemyLayoutDefinition(final Coordinate2D ...enemyLocations) {
        this.enemyLocations = Arrays.asList(enemyLocations);
    }

    public EnemyLayout getLayout(final Container container) {
        return new EnemyLayout(container, enemyLocations);
    }
}
