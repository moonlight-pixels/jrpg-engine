package com.moonlightpixels.jrpg.ui.internal;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.Optional;

public final class DefaultUiStyle implements UiStyle {
    private final Skin skin;

    public DefaultUiStyle() {
        this(new Skin());
    }

    public DefaultUiStyle(final Skin skin) {
        this.skin = skin;
    }

    @Override
    public <T> T get(final String style, final Class<T> type) {
        return Optional.ofNullable(skin.optional(style, type)).orElse(skin.get(DEFAULT_STYLE, type));
    }

    @Override
    public <T> T get(final Class<T> type) {
        return skin.get(DEFAULT_STYLE, type);
    }

    @Override
    public <T> void set(final String style, final T value) {
        skin.add(style, value);
    }
}
