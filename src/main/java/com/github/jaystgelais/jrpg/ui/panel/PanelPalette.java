package com.github.jaystgelais.jrpg.ui.panel;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by jgelais on 2/15/17.
 */
public final class PanelPalette {
    public static final Color DEFAULT_BACKGROUND_PRIMARY = new Color(0x000088ff);
    public static final Color DEFAULT_BORDER_PRIMARY = new Color(0xffffffff);
    public static final Color DEFAULT_EDGE_LIGHT = new Color(0xbbbbbbff);
    public static final Color DEFAULT_EDGE_MEDIUM = new Color(0x888888ff);
    public static final Color DEFAULT_EDGE_DARK = new Color(0x444444ff);

    private Color bgPrimary = DEFAULT_BACKGROUND_PRIMARY;
    private Color borderPrimary = DEFAULT_BORDER_PRIMARY;
    private Color borderEdgeLight = DEFAULT_EDGE_LIGHT;
    private Color borderEdgeMedium = DEFAULT_EDGE_MEDIUM;
    private Color borderEdgeDark = DEFAULT_EDGE_DARK;

    public Color getBgPrimary() {
        return bgPrimary;
    }

    public void setBgPrimary(final Color bgPrimary) {
        this.bgPrimary = bgPrimary;
    }

    public Color getBorderPrimary() {
        return borderPrimary;
    }

    public void setBorderPrimary(final Color borderPrimary) {
        this.borderPrimary = borderPrimary;
    }

    public Color getBorderEdgeLight() {
        return borderEdgeLight;
    }

    public void setBorderEdgeLight(final Color borderEdgeLight) {
        this.borderEdgeLight = borderEdgeLight;
    }

    public Color getBorderEdgeMedium() {
        return borderEdgeMedium;
    }

    public void setBorderEdgeMedium(final Color borderEdgeMedium) {
        this.borderEdgeMedium = borderEdgeMedium;
    }

    public Color getBorderEdgeDark() {
        return borderEdgeDark;
    }

    public void setBorderEdgeDark(final Color borderEdgeDark) {
        this.borderEdgeDark = borderEdgeDark;
    }
}
