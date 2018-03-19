package com.github.jaystgelais.jrpg.ui;

public final class ScreenRegion {
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public ScreenRegion(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
