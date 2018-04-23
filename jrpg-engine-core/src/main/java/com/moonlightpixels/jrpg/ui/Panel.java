package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.google.common.base.Preconditions;

/**
 * A Panel is a UI Container with a background image. Panels are the primary building blocks of user interfaces.
 *
 * @param <T> Type of Panel Content
 */
public final class Panel<T extends Actor> extends Container<T> {

    /**
     * Creates an empty panel.
     *
     * @param style PanelStyle for this panel.
     */
    public Panel(final PanelStyle style) {
        setStyle(style);
    }

    /**
     * Creates a panel and sets its content.
     *
     * @param content Panel content.
     * @param style PanelStyle for this panel.
     */
    public Panel(final T content, final PanelStyle style) {
        super(content);
        setStyle(style);
    }

    /**
     * Sets the panels style to the given style.
     *
     * @param style PanelStyle for this panel.
     */
    public void setStyle(final PanelStyle style) {
        Preconditions.checkNotNull(style);
        Preconditions.checkNotNull(style.background);
        setBackground(style.background);
        invalidateHierarchy();
    }

    /**
     * Style data for Panels.
     */
    public static final class PanelStyle {
        private final Drawable background;

        /**
         * Creates a new PanelStyle with the given background.
         *
         * @param background Panel background.
         */
        public PanelStyle(final Drawable background) {
            this.background = background;
        }

        /**
         * Creates a new PanelStyle, copying style from another PanelStyle instance.
         *
         * @param panelStyle Instance to copy.
         */
        public PanelStyle(final PanelStyle panelStyle) {
            this.background = panelStyle.background;
        }

        /**
         * Get Drawable for Panel background.
         *
         * @return Background Drawable.
         */
        public Drawable getBackground() {
            return background;
        }
    }
}
