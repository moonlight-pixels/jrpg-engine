package com.github.jaystgelais.jrpg.ui.text.transition;

import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface TextTransitionHandler extends Updatable, Renderable {
    boolean isComplete();
}
