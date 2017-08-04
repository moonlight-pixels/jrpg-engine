package com.github.jaystgelais.jrpg.ui.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;

public final class Label extends AbstractContent {
    private final String text;
    private final BitmapFont font;

    public Label(final Container parent, final FontSet fontSet, final String text) {
        super(
                parent.getContentPositionX(), parent.getContentPositionY(),
                parent.getContentWidth(), parent.getContentHeight()
        );
        this.text = text;
        this.font = fontSet.getTextFont();
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        font.draw(
                graphicsService.getSpriteBatch(),
                text,
                graphicsService.getCameraOffsetX() + getScreenPositionX(),
                graphicsService.getCameraOffsetY() + getScreenPositionY()
                        + (getHeight() / 2) + (font.getLineHeight() / 2),
                getWidth(),
                Align.center,
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
}
