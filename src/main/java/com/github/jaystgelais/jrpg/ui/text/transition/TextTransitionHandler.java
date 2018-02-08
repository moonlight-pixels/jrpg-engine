package com.github.jaystgelais.jrpg.ui.text.transition;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface TextTransitionHandler extends Drawable, Updatable {
    boolean isComplete();
}
