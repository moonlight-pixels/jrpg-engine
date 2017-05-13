package com.github.jaystgelais.jrpg.ui.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;

public final class FontSet {
    public static final float DEFAULT_FONT_TARGET_LINES = 18f;
    public static final String DEFAULT_TEXT_FONT_TTF = "assets/jrpg/fonts/Roboto-Regular.ttf";
    public static final String DEFAULT_NUMBER_FONT_TTF = "assets/jrpg/fonts/SilomBol.ttf";

    private final BitmapFont textFont;
    private final BitmapFont numberFont;

    public static Builder newFontSet(final GraphicsService graphicsService) {
        return new Builder(graphicsService);
    }

    private FontSet(final BitmapFont textFont, final BitmapFont numberFont) {
        this.textFont = textFont;
        this.numberFont = numberFont;
    }

    public BitmapFont getTextFont() {
        return textFont;
    }

    public BitmapFont getNumberFont() {
        return numberFont;
    }

    public static final class Builder {
        private final GraphicsService graphicsService;
        private BitmapFont textFont;
        private BitmapFont numberFont;

        private Builder(final GraphicsService graphicsService) {
            this.graphicsService = graphicsService;
        }

        public Builder setTextFont(final BitmapFont textFont) {
            this.textFont = textFont;
            return this;
        }

        public Builder setNumberFont(final BitmapFont numberFont) {
            this.numberFont = numberFont;
            return this;
        }

        public FontSet create() {
            return new FontSet(
                    (textFont != null) ? textFont : getDefaultTextFont(),
                    (numberFont != null) ? numberFont : getDefaultNumberFont()
            );
        }

        private BitmapFont getDefaultTextFont() {
            return createFontFromTTF(Gdx.files.internal(DEFAULT_TEXT_FONT_TTF), false);
        }

        private BitmapFont getDefaultNumberFont() {
            return createFontFromTTF(Gdx.files.internal(DEFAULT_NUMBER_FONT_TTF), true);
        }

        private BitmapFont createFontFromTTF(final FileHandle ttf, final boolean isMono) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(ttf);
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = calculateFontSize();
            parameter.mono = isMono;
            BitmapFont font = generator.generateFont(parameter);
            font.getData().markupEnabled = true;
            generator.dispose();

            return font;
        }

        private int calculateFontSize() {
            return Math.round(graphicsService.getResolutionHeight() / DEFAULT_FONT_TARGET_LINES);
        }
    }
}
