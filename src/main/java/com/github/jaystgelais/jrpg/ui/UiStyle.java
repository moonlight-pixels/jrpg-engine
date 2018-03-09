package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.graphics.Color;
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
        final TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(
                new TextureRegion(loadTexture("assets/jrpg/panel/background.png"))
        );

        final TextureRegionDrawable knobBeforeDrawable = new TextureRegionDrawable(
                new TextureRegion(loadTexture("assets/jrpg/panel/foreground.png"))
        );

        ProgressBar.ProgressBarStyle defaultProgressBarStyle = new ProgressBar.ProgressBarStyle();
        defaultProgressBarStyle.background = backgroundDrawable;
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
