package com.github.jaystgelais.jrpg.ui.panel;

/**
 * Created by jgelais on 2/18/17.
 */
public interface PanelContainer {
    float getContainerPositionX();
    float getContainerPositionY();
    float getContainerWidth();
    float getContainerHeight();
    void close();
}
