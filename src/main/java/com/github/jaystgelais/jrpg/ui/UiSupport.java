package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.jaystgelais.jrpg.Game;

public final class UiSupport {

    private UiSupport() { }

    public static <T extends Actor> Panel<T> createPanel(final T content) {
        final Panel<T> panel = new Panel<>(content, UiStyle.get(Panel.PanelStyle.class));
        return panel;
    }

    public static SelectItem<Label> createLabelMenuItem(final String text, final SelectItem.Action action) {
        return createLabelMenuItem(text, action, null);
    }

    public static SelectItem<Label> createLabelMenuItem(final String text, final SelectItem.Action action,
                                                        final SelectItem.Action onSelect) {
        return new SelectItem<Label>(createSimpleLable(text), action, onSelect);
    }

    public static Label createSimpleLable(final String text) {
        return new Label(text, UiStyle.get(Label.LabelStyle.class));
    }

    public static Label createNumericLable(final String text) {
        final BitmapFont font = Game.getInstance().getGraphicsService().getFontSet().getNumberFont();
        final Label.LabelStyle style = new Label.LabelStyle(font, font.getColor());
        return new Label(text, style);
    }
}
