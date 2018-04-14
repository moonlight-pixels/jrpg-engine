package com.moonlightpixels.jrpg.legacy.ui.text.transition;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface TextTransitionHandler extends Drawable, Updatable {
    boolean isComplete();
}
