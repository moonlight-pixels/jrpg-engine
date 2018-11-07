package com.moonlightpixels.jrpg.ui.internal;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.moonlightpixels.jrpg.internal.gdx.factory.LabelFactory;
import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.Optional;

public final class DefaultUiStyle implements UiStyle {
    private final Skin skin = new Skin();
    private final LabelFactory labelFactory;

    public DefaultUiStyle(final LabelFactory labelFactory) {
        this.labelFactory = labelFactory;
    }

    @Override
    public <T> T get(final String style, final Class<T> type) {
        return Optional.ofNullable(skin.optional(style, type)).orElseGet(() -> skin.get(DEFAULT_STYLE, type));
    }

    @Override
    public <T> T get(final Class<T> type) {
        return skin.get(DEFAULT_STYLE, type);
    }

    @Override
    public <T> void set(final String style, final T value) {
        skin.add(style, value);
    }

    @Override
    public Label createLabel(final String text, final String style) {
        return labelFactory.create(text, get(style, Label.LabelStyle.class));
    }
}
