package com.moonlightpixels.jrpg.ui.internal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.moonlightpixels.jrpg.ui.Panel;
import com.moonlightpixels.jrpg.ui.UiStyle;

import java.util.function.Supplier;

public final class DefaultUiStyleSupplier implements Supplier<UiStyle> {
    @Override
    public UiStyle get() {
        UiStyle uiStyle = new DefaultUiStyle();
        uiStyle.set(UiStyle.DEFAULT_STYLE, new BitmapFont());
        uiStyle.set(UiStyle.DEFAULT_STYLE, new Panel.PanelStyle(getDefaultPanelBackground()));
        return uiStyle;
    }

    private static Drawable getDefaultPanelBackground() {
        final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        return new SpriteDrawable(new Sprite(new Texture(pixmap)));
    }
}
