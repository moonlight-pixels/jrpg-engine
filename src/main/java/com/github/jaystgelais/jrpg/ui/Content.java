package com.github.jaystgelais.jrpg.ui;

import com.github.jaystgelais.jrpg.graphics.Renderable;

public interface Content extends Renderable {
    int getWidth();
    int getHeight();
    int getScreenPositionX();
    int getScreenPositionY();
    int getLeftMargin();
    int getRightMargin();
    int getTopMargin();
    int getBottomMargin();
    void setLeftMargin(int px);
    void setRightMargin(int px);
    void setTopMargin(int px);
    void setBottomMargin(int px);
    void setLeftMargin(float percent);
    void setRightMargin(float percent);
    void setTopMargin(float percent);
    void setBottomMargin(float percent);
}
