package com.github.jaystgelais.jrpg.combat.render;

import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.LegacyContainer;

import java.util.List;

public final class EnemyLayout extends AbstractContent {
    private final List<Coordinate2D> enemyLocations;

    public EnemyLayout(final LegacyContainer legacyContainer, final List<Coordinate2D> enemyLocations) {
        super(
                legacyContainer.getContentPositionX(),
                legacyContainer.getContentPositionY(),
                legacyContainer.getContentWidth(),
                legacyContainer.getContentHeight()
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
