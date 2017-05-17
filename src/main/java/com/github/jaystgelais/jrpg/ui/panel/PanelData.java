package com.github.jaystgelais.jrpg.ui.panel;

/**
 * Created by jgelais on 2/21/17.
 */
public final class PanelData {
    public static final int DEFAULT_POSITION_X = 0;
    public static final int DEFAULT_POSITION_Y = 0;
    public static final long DEFAUL_TRANSITION_TIME_MS = 0;

    private final int width;
    private final int height;
    private final PanelPalette palette;
    private int positionX = DEFAULT_POSITION_X;
    private int positionY = DEFAULT_POSITION_Y;
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

    public int getPositionX() {
        return positionX;
    }

    public PanelData setPositionX(final int positionX) {
        this.positionX = positionX;
        return this;
    }

    public int getPositionY() {
        return positionY;
    }

    public PanelData setPositionY(final int positionY) {
        this.positionY = positionY;
        return this;
    }

    public long getTransitionTimeMs() {
        return transitionTimeMs;
    }

    public PanelData setTransitionTimeMs(final long transitionTimeMs) {
        this.transitionTimeMs = transitionTimeMs;
        return this;
    }
}
