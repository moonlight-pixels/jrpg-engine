package com.moonlightpixels.jrpg.ui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Utility for creating BitmapFont instances.
 */
public final class FontUtil {
    private FontUtil() { }

    /**
     * Creates a BitmapFont from a TTF file.
     *
     * @param ttfPath path to font source
     * @param size font size to create
     * @param isMono If true, font smoothing is disabled
     * @return BitmapFont instance
     */
    public static BitmapFont fromTTF(final String ttfPath, final int size, final boolean isMono) {
        final FileHandle ttf = Gdx.files.internal(ttfPath);
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(ttf);
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.mono = isMono;

        final BitmapFont font = generator.generateFont(parameter);
        font.getData().markupEnabled = true;
        generator.dispose();

        return font;
    }
}
