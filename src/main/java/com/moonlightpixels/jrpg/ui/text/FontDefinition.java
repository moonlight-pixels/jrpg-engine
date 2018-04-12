package com.moonlightpixels.jrpg.ui.text;

public final class FontDefinition {
    private final String fontPath;
    private final int fontSize;
    private final boolean isMono;

    public FontDefinition(final String fontPath, final int fontSize, final boolean isMono) {
        this.fontPath = fontPath;
        this.fontSize = fontSize;
        this.isMono = isMono;
    }

    public String getFontPath() {
        return fontPath;
    }

    public int getFontSize() {
        return fontSize;
    }

    public boolean isMono() {
        return isMono;
    }
}
