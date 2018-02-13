package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.github.jaystgelais.jrpg.Game;
import com.google.common.base.Preconditions;

public final class Panel<T extends Actor> extends Container<T> {
    private static final String DEFAULT_BACKGROUND_9PATCH = "assets/jrpg/panel/gradient_panel.png";
    private static final int DEFAULT_PATCH_SIZE = 3;
    private static final float CENTER_TEXTURE_OFFSET_X1 = 0.38f;
    private static final float CENTER_TEXTURE_OFFSET_Y1 = 0.38f;
    private static final float CENTER_TEXTURE_OFFSET_X2 = 0.61f;
    private static final float CENTER_TEXTURE_OFFSET_Y2 = 0.61f;

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

        Texture panelTexture = assetManager.get(DEFAULT_BACKGROUND_9PATCH, Texture.class);
        TextureRegion[][] patches = TextureRegion.split(panelTexture, DEFAULT_PATCH_SIZE, DEFAULT_PATCH_SIZE);
        TextureRegion centerRegion = patches[1][1];
        centerRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        centerRegion.setRegion(
                CENTER_TEXTURE_OFFSET_X1, CENTER_TEXTURE_OFFSET_Y1,
                CENTER_TEXTURE_OFFSET_X2, CENTER_TEXTURE_OFFSET_Y2
        );

        return new NinePatch(
                patches[0][0],
                patches[0][1],
                patches[0][2],
                patches[1][0],
                patches[1][1],
                patches[1][2],
                patches[2][0],
                patches[2][1],
                patches[2][2]
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
