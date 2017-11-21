package com.github.jaystgelais.jrpg.combat.render;

import com.badlogic.gdx.graphics.Texture;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;

public final class CombatRenderer implements Renderable {
    private final Texture background;

    public CombatRenderer(final Texture background) {
        this.background = background;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        graphicsService.renderStart();
        graphicsService.drawBackground(background);
        graphicsService.renderEnd();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
