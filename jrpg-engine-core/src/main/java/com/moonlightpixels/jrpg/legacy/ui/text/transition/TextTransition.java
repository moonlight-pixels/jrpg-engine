package com.moonlightpixels.jrpg.legacy.ui.text.transition;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.moonlightpixels.jrpg.legacy.ui.text.TextDisplay;

public interface TextTransition {
    TextTransitionHandler handleTransition(TextDisplay parent, GlyphLayout oldText, GlyphLayout newText);
}
