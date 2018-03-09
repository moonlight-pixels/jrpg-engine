package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;

import java.util.Optional;

public final class UiStyle {
    public static final String DEFAULT_STYLE = "default";
    public static final String NUMBER_STYLE = "number";

    private static final Skin SKIN = new Skin();
    static {
        initDefaultTextStyles();
        initDefaultNumericTextStyles();
        initDefaultPanelStyle();
        initDefaultProgressBarStyle();
    }

    private UiStyle() { }

    public static <T> T get(final String style, final Class<T> type) {
        return Optional.ofNullable(SKIN.optional(style, type)).orElse(SKIN.get(DEFAULT_STYLE, type));
    }

    public static <T> T get(final Class<T> type) {
        return SKIN.get(DEFAULT_STYLE, type);
    }

    public static <T> void set(final String style, final T value) {
        SKIN.add(style, value);
    }

    private static void initDefaultPanelStyle() {
        final Panel.PanelStyle defaultPanelStyle = new Panel.PanelStyle(Panel.getDefaultBackground());
        SKIN.add(DEFAULT_STYLE, defaultPanelStyle);
    }

    private static void initDefaultNumericTextStyles() {
        final BitmapFont numberTextFont = Game.getInstance().getGraphicsService().getFontSet().getNumberFont();
        final Label.LabelStyle numberLabelStyle = new Label.LabelStyle(numberTextFont, Color.WHITE);
        SKIN.add(NUMBER_STYLE, numberTextFont);
        SKIN.add(NUMBER_STYLE, numberLabelStyle);
    }

    private static void initDefaultTextStyles() {
        final BitmapFont defaultTextFont = Game.getInstance().getGraphicsService().getFontSet().getTextFont();
        final Label.LabelStyle defaultLabelStyle = new Label.LabelStyle(defaultTextFont, Color.WHITE);
        SKIN.add(DEFAULT_STYLE, defaultTextFont);
        SKIN.add(DEFAULT_STYLE, defaultLabelStyle);
    }

    private static void initDefaultProgressBarStyle() {
        final Pixmap backgroundPixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.WHITE);
        backgroundPixmap.fill();
        final TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(
                new TextureRegion(new Texture(backgroundPixmap))
        );
        backgroundPixmap.dispose();

        final Pixmap knobPixmap = new Pixmap(0, 18, Pixmap.Format.RGBA8888);
        knobPixmap.setColor(Color.GREEN);
        knobPixmap.fill();
        final TextureRegionDrawable knobDrawable = new TextureRegionDrawable(
                new TextureRegion(new Texture(knobPixmap))
        );
        knobPixmap.dispose();

        final Pixmap knobBeforePixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        knobBeforePixmap.setColor(Color.GREEN);
        knobBeforePixmap.fill();
        final TextureRegionDrawable knobBeforeDrawable = new TextureRegionDrawable(
                new TextureRegion(new Texture(knobBeforePixmap))
        );
        knobBeforePixmap.dispose();

        ProgressBar.ProgressBarStyle defaultProgressBarStyle = new ProgressBar.ProgressBarStyle();
        defaultProgressBarStyle.background = backgroundDrawable;
        defaultProgressBarStyle.knob = knobDrawable;
        defaultProgressBarStyle.knobBefore = knobBeforeDrawable;

        SKIN.add(DEFAULT_STYLE, defaultProgressBarStyle);
    }

    private static Texture loadTexture(final String pathToTexture) {
        final GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        if (!graphicsService.getAssetManager().isLoaded(pathToTexture, Texture.class)) {
            graphicsService.getAssetManager().load(pathToTexture, Texture.class);
            graphicsService.getAssetManager().finishLoading();
        }

        return graphicsService.getAssetManager().get(pathToTexture, Texture.class);
    }
}
