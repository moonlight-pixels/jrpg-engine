package com.github.jaystgelais.jrpg.ui.text.transition;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.github.jaystgelais.jrpg.ui.text.TextArea;

public interface TextTransition {
    TextTransitionHandler handleTransition(TextArea parent, GlyphLayout oldText, GlyphLayout newText);
}
