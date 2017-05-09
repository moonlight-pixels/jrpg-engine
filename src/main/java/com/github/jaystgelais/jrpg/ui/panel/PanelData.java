package com.github.jaystgelais.jrpg.ui.panel;

/**
 * Created by jgelais on 2/21/17.
 */
public final class PanelData {
    public static final int DEFAULT_POSITION_X = 0;
    public static final int DEFAULT_POSITION_Y = 0;
    public static final long DEFAUL_TRANSITION_TIME_MS = 250;

    private final int width;
    private final int height;
    private final PanelPalette palette;
    private float positionX = DEFAULT_POSITION_X;
    private float positionY = DEFAULT_POSITION_Y;
    private long transitionTimeMs = DEFAUL_TRANSITION_TIME_MS;

    public PanelData(final int width, final int height, final PanelPalette palette) {
        this.width = width;
        this.height = height;
        this.palette = palette;
    }

    public PanelData(final int width, final int height) {
        this(width, height, new PanelPalette());
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PanelPalette getPalette() {
        return palette;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(final float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(final float positionY) {
        this.positionY = positionY;
    }

    public long getTransitionTimeMs() {
        return transitionTimeMs;
    }

    public void setTransitionTimeMs(final long transitionTimeMs) {
        this.transitionTimeMs = transitionTimeMs;
    }
}
