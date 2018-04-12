package com.moonlightpixels.jrpg.ui.text.transition;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.moonlightpixels.jrpg.tween.IntegerTween;
import com.moonlightpixels.jrpg.ui.text.TextDisplay;

public final class TypedTextTransition implements TextTransition {
    public static final long DEFAULT_TIME_PER_GLYPH_MS = 50;

    private final long timePerGlyphMs;

    public TypedTextTransition() {
        this(DEFAULT_TIME_PER_GLYPH_MS);
    }

    public TypedTextTransition(final long timePerGlyphMs) {
        this.timePerGlyphMs = timePerGlyphMs;
    }

    @Override
    public TextTransitionHandler handleTransition(final TextDisplay parent, final GlyphLayout oldText,
                                                  final GlyphLayout newText) {
        return new Handler(parent, newText, timePerGlyphMs);
    }

    private static class Handler extends BaseDrawable implements TextTransitionHandler {
        private final TextDisplay parent;
        private final GlyphLayout newText;
        private final long timePerGlyphMs;
        private final IntegerTween glyphCountTween;

        Handler(final TextDisplay parent, final GlyphLayout newText, final long timePerGlyphMs) {
            this.parent = parent;
            this.newText = newText;
            this.timePerGlyphMs = timePerGlyphMs;
            int glyphCount = 0;
            for (GlyphLayout.GlyphRun run : newText.runs) {
                glyphCount += run.glyphs.size;
            }
            glyphCountTween = new IntegerTween(0, glyphCount, glyphCount * this.timePerGlyphMs);
        }

        @Override
        public void update(final long elapsedTime) {
            glyphCountTween.update(elapsedTime);
        }

        @Override
        public void draw(final Batch batch, final float x, final float y, final float width, final float height) {
            final BitmapFontCache fontCache = new BitmapFontCache(parent.getFont());
            fontCache.addText(newText, x, y + height);
            fontCache.draw(batch, 0, glyphCountTween.getValue());
        }

        @Override
        public boolean isComplete() {
            return glyphCountTween.isComplete();
        }
    }
}
