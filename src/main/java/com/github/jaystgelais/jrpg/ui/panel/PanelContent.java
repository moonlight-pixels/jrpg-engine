package com.github.jaystgelais.jrpg.ui.panel;

import com.github.jaystgelais.jrpg.graphics.Renderable;

/**
 * Created by jgelais on 2/18/17.
 */
public abstract class PanelContent implements Renderable {
    private final PanelContainer parent;

    public PanelContent(final PanelContainer parent) {
        this.parent = parent;
    }

    public abstract float getRelativeX();

    public abstract float getRelativeY();

    public final float getAbsoluteX() {
        return parent.getContainerPositionX() + getRelativeX();
    }

    public final float getAbsoluteY() {
        return parent.getContainerPositionY() + getRelativeY();
    }

    public final PanelContainer getParent() {
        return parent;
    }
}
