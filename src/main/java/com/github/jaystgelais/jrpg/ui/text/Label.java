package com.github.jaystgelais.jrpg.ui.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.LegacyContainer;

public final class Label extends AbstractContent {
    private final String text;
    private final BitmapFont font;
    private final int alignment;

    public Label(final LegacyContainer parent, final BitmapFont font, final String text) {
        this(parent, font, text, Align.center);
    }

    public Label(final LegacyContainer parent, final BitmapFont font, final String text, final int alignment) {
        super(
                parent.getContentPositionX(), parent.getContentPositionY(),
                parent.getContentWidth(), parent.getContentHeight()
        );
        this.text = text;
        this.font = font;
        this.alignment = alignment;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        font.draw(
                graphicsService.getSpriteBatch(),
                text,
                graphicsService.getCameraOffsetX() + getScreenPositionX(),
                graphicsService.getCameraOffsetY() + getAlignedScreenPositionY(),
                getWidth(),
                alignment,
                false
        );
    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(final long elapsedTime) {

    }

    @Override
    public void handleInput(final InputService inputService) {

    }

    private float getAlignedScreenPositionY() {
        switch (alignment) {
            case Align.topLeft:
            case Align.top:
            case Align.topRight:
                return getScreenPositionY() + getHeight();
            case Align.left:
            case Align.center:
            case Align.right:
                return getScreenPositionY() + (getHeight() / 2) + (font.getLineHeight() / 2);
            case Align.bottomLeft:
            case Align.bottom:
            case Align.bottomRight:
                return getScreenPositionY() + font.getLineHeight();
            default:
                return 0;
        }
    }
}
