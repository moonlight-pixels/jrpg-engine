package com.github.jaystgelais.jrpg.ui.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;

public final class SpriteArea extends AbstractContent {
    private final TextureRegion sprite;

    public SpriteArea(final Container parent, final TextureRegion sprite) {
        super(
                parent.getContentPositionX(), parent.getContentPositionY(),
                parent.getContentWidth(), parent.getContentHeight()
        );
        this.sprite = sprite;
    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public void handleInput(final InputService inputService) {

    }

    @Override
    public void render(final GraphicsService graphicsService) {
        graphicsService.drawSprite(
                sprite,
                graphicsService.getCameraOffsetX() + getScreenPositionX()
                        + (getWidth() / 2.0f) - (sprite.getRegionWidth() / 2.0f),
                graphicsService.getCameraOffsetY() + getScreenPositionY()
                        + (getHeight() / 2.0f) - (sprite.getRegionHeight() / 2.0f)
        );
    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }

    @Override
    public void dispose() {

    }
}
