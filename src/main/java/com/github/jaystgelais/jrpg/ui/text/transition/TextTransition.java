package com.github.jaystgelais.jrpg.ui.text.transition;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.github.jaystgelais.jrpg.ui.text.TextDisplay;

public interface TextTransition {
    TextTransitionHandler handleTransition(TextDisplay parent, GlyphLayout oldText, GlyphLayout newText);
}
