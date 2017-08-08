package com.github.jaystgelais.jrpg.ui.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;

public class ProgressBar extends AbstractContent {
    private final int maxValue;
    private int currentValue;
    private Texture background;
    private Texture foreground;
    private Integer scaledWidth;

    public ProgressBar(final Container parent, final int maxValue, final int currentValue) {
        super(
                parent.getContentPositionX(), parent.getContentPositionY(),
                parent.getContentWidth(), parent.getContentHeight()
        );
        this.maxValue = maxValue;
        this.currentValue = currentValue;
    }

    public ProgressBar(final Container parent, final int maxValue, final int currentValue, final int scaledWdth) {
        this(parent, maxValue, currentValue);
        this.scaledWidth = scaledWdth;
    }


    @Override
    public void render(final GraphicsService graphicsService) {
        SpriteBatch spriteBatch = graphicsService.getSpriteBatch();
        int offsetX = graphicsService.getCameraOffsetX();
        int offsetY = graphicsService.getCameraOffsetY();
        float percentFull = (float) currentValue / (float) maxValue;

        spriteBatch.draw(
                getBackground(graphicsService),
                getProgressBarXPoistion(graphicsService),
                getProgressBarYPoistion(graphicsService),
                getProgressBarWidth(graphicsService),
                getProgressBarHeight(graphicsService),
                0.0f, 0.0f, 1.0f, 1.0f
        );
        spriteBatch.draw(
                getForeground(graphicsService),
                getProgressBarXPoistion(graphicsService),
                getProgressBarYPoistion(graphicsService),
                getProgressBarWidth(graphicsService) * percentFull,
                getProgressBarHeight(graphicsService),
                0.0f, 0.0f, percentFull, 1.0f
        );
    }

    @Override
    public void dispose() {

    }

    private int getProgressBarXPoistion(final GraphicsService graphicsService) {
        return graphicsService.getCameraOffsetX() + getScreenPositionX() + (getWidth() / 2) - (getProgressBarWidth(graphicsService) / 2);
    }

    private int getProgressBarYPoistion(final GraphicsService graphicsService) {
        return graphicsService.getCameraOffsetY() + getScreenPositionY() + (getHeight() / 2) - (getProgressBarHeight(graphicsService) / 2);
    }

    private int getProgressBarWidth(final GraphicsService graphicsService) {
        return (scaledWidth != null)
                ? scaledWidth
                :  getBackground(graphicsService).getWidth();
    }

    private int getProgressBarHeight(final GraphicsService graphicsService) {
        float scalingFactor = (scaledWidth != null)
                ? (float) scaledWidth / (float) getBackground(graphicsService).getWidth()
                :  1.0f;

        return (int) Math.round(scalingFactor * getBackground(graphicsService).getHeight());
    }

    private Texture getBackground(final GraphicsService graphicsService) {
        if (background == null) {
            background = loadTexture(graphicsService, "assets/jrpg/panel/background.png");
        }

        return background;
    }

    private Texture getForeground(final GraphicsService graphicsService) {
        if (foreground == null) {
            foreground = loadTexture(graphicsService, "assets/jrpg/panel/foreground.png");
        }

        return foreground;
    }

    private Texture loadTexture(GraphicsService graphicsService, String pathToTexture) {
        if (!graphicsService.getAssetManager().isLoaded(pathToTexture, Texture.class)) {
            graphicsService.getAssetManager().load(pathToTexture, Texture.class);
            graphicsService.getAssetManager().finishLoading();
        }

        return graphicsService.getAssetManager().get(pathToTexture, Texture.class);
    }

    @Override
    public void update(long elapsedTime) {

    }

    @Override
    public void handleInput(InputService inputService) {

    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }
}
