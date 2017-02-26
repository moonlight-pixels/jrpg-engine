package com.github.jaystgelais.jrpg.ui.panel;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;

/**
 * Created by jgelais on 2/18/17.
 */
public abstract class PanelContent implements Renderable, InputHandler {
    private PanelContainer parent;

    public abstract float getRelativeX();

    public abstract float getRelativeY();

    public final float getAbsoluteX() {
        return parent.getContainerPositionX() + getRelativeX();
    }

    public final float getAbsoluteY() {
        return parent.getContainerPositionY() + getRelativeY();
    }

    public final PanelContainer getParent() {
        if (parent == null) {
            throw new IllegalStateException("Panel Content must be added to a PanelContainer.");
        }
        return parent;
    }

    final void setParent(final PanelContainer parent) {
        this.parent = parent;
    }
}
