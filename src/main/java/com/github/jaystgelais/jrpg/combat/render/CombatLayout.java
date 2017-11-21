package com.github.jaystgelais.jrpg.combat.render;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.ui.Layout;
import com.google.common.base.Preconditions;

public final class CombatLayout implements Renderable {
    public static final String SECTION_PLAYERS = "players";
    public static final String SECTION_ENEMIES = "enemies";
    public static final String SECTION_UI = "ui";
    public static final String SECTION_MESSAGES = "messages";

    private final Layout parentLayout;
    private final Layout playerLayout;
    private final Layout enemyLayout;

    public CombatLayout(final Layout parentLayout, final Layout playerLayout, final Layout enemyLayout) {
        Preconditions.checkNotNull(parentLayout.getContainer(SECTION_PLAYERS));
        Preconditions.checkNotNull(parentLayout.getContainer(SECTION_ENEMIES));
        Preconditions.checkNotNull(parentLayout.getContainer(SECTION_UI));
        Preconditions.checkNotNull(parentLayout.getContainer(SECTION_MESSAGES));
        this.parentLayout = parentLayout;
        this.playerLayout = playerLayout;
        this.enemyLayout = enemyLayout;
        parentLayout.getContainer(SECTION_PLAYERS).setContent(playerLayout);
        parentLayout.getContainer(SECTION_ENEMIES).setContent(enemyLayout);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        parentLayout.render(graphicsService);
    }

    @Override
    public void dispose() {
        parentLayout.dispose();
    }
}
