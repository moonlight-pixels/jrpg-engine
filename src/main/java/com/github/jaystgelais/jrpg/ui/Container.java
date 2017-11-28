package com.github.jaystgelais.jrpg.ui;

import com.github.jaystgelais.jrpg.graphics.Coordinate2D;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;

public final class Container extends AbstractContent {
    private Content content;

    public Container(final int screenPositionX, final int screenPositionY, final int width, final int height) {
        super(screenPositionX, screenPositionY, width, height);
    }

    @Override
    protected boolean canChangeMargins() {
        return (content == null);
    }

    public Content getContent() {
        return content;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        if (content != null) {
            content.render(graphicsService);
        }
    }

    @Override
    public void dispose() {
        if (content != null) {
            content.dispose();
        }
    }

    @Override
    public void update(final long elapsedTime) {
        if (content != null) {
            content.update(elapsedTime);
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
            if (content != null) {
                content.handleInput(inputService);
            }
    }

    public Coordinate2D getCenter() {
        return new Coordinate2D(
                getContentPositionX() + (getContentWidth() / 2),
                getContentPositionY() + (getContentHeight() / 2)
        );
    }
}
