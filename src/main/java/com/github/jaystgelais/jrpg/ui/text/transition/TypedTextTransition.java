package com.github.jaystgelais.jrpg.ui.text.transition;

import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.tween.IntegerTween;
import com.github.jaystgelais.jrpg.ui.text.TextArea;

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
    public TextTransitionHandler handleTransition(final TextArea parent, final GlyphLayout oldText, final GlyphLayout newText) {
        return new Handler(parent, newText, timePerGlyphMs);
    }

    private static class Handler implements TextTransitionHandler {
        private final BitmapFontCache fontCache;
        private final long timePerGlyphMs;
        private final IntegerTween glyphCountTween;

        Handler(final TextArea parent, final GlyphLayout newText, final long timePerGlyphMs) {
            fontCache = new BitmapFontCache(parent.getFont());
            fontCache.addText(newText, parent.getContentPositionX(), parent.getContentPositionY() + parent.getHeight());
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
        public void render(final GraphicsService graphicsService) {
            fontCache.draw(graphicsService.getSpriteBatch(), 0, glyphCountTween.getValue());
        }

        @Override
        public boolean isComplete() {
            return glyphCountTween.isComplete();
        }

        @Override
        public void dispose() {

        }
    }
}
