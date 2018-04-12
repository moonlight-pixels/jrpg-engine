package com.moonlightpixels.jrpg.ui.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.moonlightpixels.jrpg.Game;
import com.moonlightpixels.jrpg.graphics.GraphicsService;

public final class FontSet {
    public static final float DEFAULT_FONT_TARGET_LINES = 22f;
    public static final String DEFAULT_TEXT_FONT_TTF = "assets/jrpg/fonts/Roboto-Regular.ttf";
    public static final String DEFAULT_NUMBER_FONT_TTF = "assets/jrpg/fonts/SilomBol.ttf";

    private final BitmapFont textFont;
    private final BitmapFont numberFont;

    public static Builder newFontSet(final GraphicsService graphicsService) {
        return new Builder(graphicsService);
    }

    public static Builder newFontSet() {
        return newFontSet(Game.getInstance().getGraphicsService());
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

        public Builder setTextFont(final FontDefinition textFont) {
            this.textFont = createFontFromTTF(
                    Gdx.files.internal(textFont.getFontPath()),
                    textFont.getFontSize(),
                    textFont.isMono()
            );
            return this;
        }

        public Builder setNumberFont(final FontDefinition numberFont) {
            this.numberFont = createFontFromTTF(
                    Gdx.files.internal(numberFont.getFontPath()),
                    numberFont.getFontSize(),
                    numberFont.isMono()
            );
            return this;
        }

        public FontSet create() {
            return new FontSet(
                    (textFont != null) ? textFont : getDefaultTextFont(),
                    (numberFont != null) ? numberFont : getDefaultNumberFont()
            );
        }

        private BitmapFont getDefaultTextFont() {
            return createFontFromTTF(Gdx.files.internal(DEFAULT_TEXT_FONT_TTF), calculateFontSize(), true);
        }

        private BitmapFont getDefaultNumberFont() {
            return createFontFromTTF(Gdx.files.internal(DEFAULT_NUMBER_FONT_TTF), calculateFontSize(), true);
        }

        private BitmapFont createFontFromTTF(final FileHandle ttf, final int size, final boolean isMono) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(ttf);
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = size;
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
