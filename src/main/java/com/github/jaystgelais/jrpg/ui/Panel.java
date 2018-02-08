package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.github.jaystgelais.jrpg.Game;
import com.google.common.base.Preconditions;

public final class Panel<T extends Actor> extends Container<T> {
    private static final String DEFAULT_BACKGROUND_9PATCH = "assets/jrpg/panel/gradient_panel.png";
    private static final int DEFAULT_BACKGROUND_BORDER_WIDTH = 3;

    public Panel(final PanelStyle style) {
        setStyle(style);
    }

    public Panel(final T actor, final PanelStyle style) {
        super(actor);
        setStyle(style);
    }

    public void setStyle(final PanelStyle style) {
        Preconditions.checkNotNull(style);
        Preconditions.checkNotNull(style.background);
        setBackground(style.background);
        invalidateHierarchy();
    }

    public static Drawable getDefaultBackground() {
        return new NinePatchDrawable(createPanelNinePatch());
    }

    private static NinePatch createPanelNinePatch() {
        final AssetManager assetManager = Game.getInstance().getGraphicsService().getAssetManager();
        if (!assetManager.isLoaded(DEFAULT_BACKGROUND_9PATCH, Texture.class)) {
            assetManager.load(DEFAULT_BACKGROUND_9PATCH, Texture.class);
            assetManager.finishLoading();
        }

        return new NinePatch(
                assetManager.get(DEFAULT_BACKGROUND_9PATCH, Texture.class),
                DEFAULT_BACKGROUND_BORDER_WIDTH,
                DEFAULT_BACKGROUND_BORDER_WIDTH,
                DEFAULT_BACKGROUND_BORDER_WIDTH,
                DEFAULT_BACKGROUND_BORDER_WIDTH
        );
    }

    public static final class PanelStyle {
        private Drawable background;

        public PanelStyle(final Drawable background) {
            this.background = background;
        }

        public PanelStyle(final PanelStyle panelStyle) {
            this.background = panelStyle.background;
        }

        public Drawable getBackground() {
            return background;
        }

        public PanelStyle setBackground(final Drawable background) {
            this.background = background;
            return this;
        }
    }
}
