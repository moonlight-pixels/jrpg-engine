package com.github.jaystgelais.jrpg.combat.render;

import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;

import java.util.List;

public final class EnemyLayout extends AbstractContent {
    private final List<Coordinate2D> enemyLocations;

    public EnemyLayout(final Container container, final List<Coordinate2D> enemyLocations) {
        super(
                container.getContentPositionX(),
                container.getContentPositionY(),
                container.getContentWidth(),
                container.getContentHeight()
        );
        this.enemyLocations = enemyLocations;
    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }

    @Override
    public void render(final GraphicsService graphicsService) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput(final InputService inputService) {

    }

    @Override
    public void update(final long elapsedTime) {

    }
}
