package com.moonlightpixels.jrpg.internal.gdx.factory;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public final class DefaultLabelFactory implements LabelFactory {
    @Override
    public Label create(final String text, final Label.LabelStyle style) {
        return new Label(text, style);
    }
}
