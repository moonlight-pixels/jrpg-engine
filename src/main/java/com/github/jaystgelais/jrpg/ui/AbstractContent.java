package com.github.jaystgelais.jrpg.ui;

public abstract class AbstractContent implements Content {
    private final int width;
    private final int height;
    private final int screenPositionX;
    private final int screenPositionY;
    private int leftMargin = 0;
    private int rightMargin = 0;
    private int topMargin = 0;
    private int bottomMargin = 0;

    public AbstractContent(final int screenPositionX, final int screenPositionY, final int width, final int height) {
        this.screenPositionY = screenPositionY;
        this.screenPositionX = screenPositionX;
        this.height = height;
        this.width = width;
    }

    protected abstract boolean canChangeMargins();

    @Override
    public final int getWidth() {
        return width;
    }

    @Override
    public final int getHeight() {
        return height;
    }

    @Override
    public final int getScreenPositionX() {
        return screenPositionX;
    }

    @Override
    public final int getScreenPositionY() {
        return screenPositionY;
    }

    @Override
    public final int getLeftMargin() {
        return leftMargin;
    }

    @Override
    public final int getRightMargin() {
        return rightMargin;
    }

    @Override
    public final int getTopMargin() {
        return topMargin;
    }

    @Override
    public final int getBottomMargin() {
        return bottomMargin;
    }

    @Override
    public final void setLeftMargin(final int px) {
        if (canChangeMargins()) {
            leftMargin = px;
        }
    }

    @Override
    public final void setRightMargin(final int px) {
        if (canChangeMargins()) {
            rightMargin = px;
        }
    }

    @Override
    public final void setTopMargin(final int px) {
        if (canChangeMargins()) {
            topMargin = px;
        }
    }

    @Override
    public final void setBottomMargin(final int px) {
        if (canChangeMargins()) {
            bottomMargin = px;
        }
    }

    @Override
    public final void setLeftMargin(final float percent) {
        setLeftMargin(Math.round(width * percent));
    }

    @Override
    public final void setRightMargin(final float percent) {
        setRightMargin(Math.round(width * percent));
    }

    @Override
    public final void setTopMargin(final float percent) {
        setTopMargin(Math.round(height * percent));
    }

    @Override
    public final void setBottomMargin(final float percent) {
        setBottomMargin(Math.round(height * percent));
    }

    public final int getContentPositionX() {
        return getScreenPositionX() + getLeftMargin();
    }

    public final int getContentPositionY() {
        return getScreenPositionY() + getBottomMargin();
    }

    public final int getContentWidth() {
        return getWidth() - (getLeftMargin() + getRightMargin());
    }

    public final int getContentHeight() {
        return getHeight() - (getBottomMargin() + getTopMargin());
    }
}
