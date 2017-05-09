package com.github.jaystgelais.jrpg.ui;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;

public final class Container extends AbstractContent {
    private Content content;

    public Container(final int screenPositionY, final int screenPositionX, final int height, final int width) {
        super(screenPositionY, screenPositionX, height, width);
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
        content.render(graphicsService);
    }

    @Override
    public void dispose() {
        content.dispose();
    }
}
