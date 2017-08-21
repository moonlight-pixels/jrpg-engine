package com.github.jaystgelais.jrpg.menu;

import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.Content;
import com.github.jaystgelais.jrpg.ui.text.Label;

public final class TextSelectItemRenderer implements SelectItemRenderer {
    private final String text;

    public TextSelectItemRenderer(final String text) {
        this.text = text;
    }

    @Override
    public Content renderItem(final GraphicsService graphicsService, final Container container) {
        return new Label(
                container,
                graphicsService.getFontSet().getTextFont(),
                text,
                Align.topLeft
        );
    }

    @Override
    public int getItemHeight(final GraphicsService graphicsService) {
        return (int) graphicsService.getFontSet().getTextFont().getLineHeight();
    }
}
