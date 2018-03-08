package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.jaystgelais.jrpg.Game;

import java.util.Optional;

public final class UiStyle {
    public static final String DEFAULT_STYLE = "default";
    public static final String NUMBER_STYLE = "number";

    private static final Skin SKIN = new Skin();
    static {
        final BitmapFont defaultTextFont = Game.getInstance().getGraphicsService().getFontSet().getTextFont();
        final Label.LabelStyle defaultLabelStyle = new Label.LabelStyle(defaultTextFont, Color.WHITE);
        SKIN.add(DEFAULT_STYLE, defaultTextFont);
        SKIN.add(DEFAULT_STYLE, defaultLabelStyle);


        final BitmapFont numberTextFont = Game.getInstance().getGraphicsService().getFontSet().getNumberFont();
        final Label.LabelStyle numberLabelStyle = new Label.LabelStyle(numberTextFont, Color.WHITE);
        SKIN.add(NUMBER_STYLE, numberTextFont);
        SKIN.add(NUMBER_STYLE, numberLabelStyle);

        final Panel.PanelStyle defaultPanelStyle = new Panel.PanelStyle(Panel.getDefaultBackground());
        SKIN.add(DEFAULT_STYLE, defaultPanelStyle);
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
}
