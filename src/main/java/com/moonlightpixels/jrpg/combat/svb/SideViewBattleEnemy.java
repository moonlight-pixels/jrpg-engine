package com.moonlightpixels.jrpg.combat.svb;

import com.badlogic.gdx.graphics.Texture;
import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.graphics.Renderable;

import java.util.Optional;

public final class SideViewBattleEnemy implements Renderable, SelectableActor {
    private final Texture image;
    private final int positionX;
    private final int positionY;
    private Texture cursor;

    public SideViewBattleEnemy(final Texture image, final int positionX, final int positionY) {
        this.image = image;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        graphicsService.drawSprite(image, positionX, positionY);
        getCursor().ifPresent(cursor -> {
            graphicsService.drawSprite(
                    cursor,
                    positionX - cursor.getWidth(),
                    positionY + (image.getHeight() / 2) - (cursor.getHeight() / 2)
            );
        });
    }

    @Override
    public void showCursor(final Texture cursor) {
        this.cursor = cursor;
    }

    @Override
    public void hideCursor() {
        cursor = null;
    }

    private Optional<Texture> getCursor() {
        return Optional.ofNullable(cursor);
    }

    @Override
    public void dispose() {

    }
}
