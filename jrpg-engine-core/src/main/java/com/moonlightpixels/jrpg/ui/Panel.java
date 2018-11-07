package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * A Panel is a UI Container with a background image. Panels are the primary building blocks of user interfaces.
 *
 * @param <T> Type of Panel Content
 */
public final class Panel<T extends Actor> extends Container<T> {
    private ActorPlacement placement;

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
        setBackground(style.background, true);
        invalidateHierarchy();
    }

    @Override
    public float getWidth() {
        return (super.getWidth() > 0f) ? super.getWidth() : getImplicitWidth();
    }

    @Override
    public float getHeight() {
        return (super.getHeight() > 0f) ? super.getHeight() : getImplicitHeight();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
        getPlacement().ifPresent(placement -> {
            setX(placement.getX(this));
            setY(placement.getY(this));
        });
    }

    /**
     * Placement specification for this panel.
     *
     * @return Placement as Optional.
     */
    public Optional<ActorPlacement> getPlacement() {
        return Optional.ofNullable(placement);
    }

    /**
     * Placement specification for this panel.
     *
     * @param placement placement specification for this panel
     */
    public void setPlacement(final ActorPlacement placement) {
        this.placement = placement;
    }

    private float getImplicitWidth() {
        return (getActor() != null) ? (getActor().getWidth() + getPadLeft() + getPadRight()) : 0f;
    }

    private float getImplicitHeight() {
        return (getActor() != null) ? (getActor().getHeight() + getPadTop() + getPadBottom()) : 0f;
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
