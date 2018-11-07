package com.moonlightpixels.jrpg.internal.gdx.factory;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public interface LabelFactory {
    Label create(String text, Label.LabelStyle style);
}
