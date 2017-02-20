package com.github.jaystgelais.jrpg.ui.panel;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jgelais on 2/18/17.
 */
public class PanelText extends PanelContent {
    private final BitmapFont font;
    private Queue<String> words = new LinkedList<>();
    private GlyphLayout layout;

    public PanelText(final PanelContainer parent, final BitmapFont font, final String text) {
        super(parent);
        this.font = font;
        words.addAll(Arrays.asList(text.split(" ")));
        layout = generateGlyphLayout("");
        updatePage();
    }

    @Override
    public final void render(final GraphicsService graphicsService) {
        font.draw(
                graphicsService.getSpriteBatch(), layout,
                getAbsoluteX(), getAbsoluteY() + getParent().getContainerHeight()
        );
    }

    public final void updatePage() {
        if (!words.isEmpty()) {
            String displayText = words.peek();
            boolean isPageFull = false;
            while (!words.isEmpty() && !isPageFull) {
                GlyphLayout nextLayout = generateGlyphLayout(displayText);
                if (nextLayout.height < getParent().getContainerHeight()) {
                    layout = nextLayout;
                    words.remove();
                    displayText = String.join(" ", displayText, words.peek());
                } else {
                    isPageFull = true;
                }
            }
        }
    }

    @Override
    public final float getRelativeX() {
        return 0;
    }

    @Override
    public final float getRelativeY() {
        return 0;
    }

    @Override
    public void dispose() {

    }


    private GlyphLayout generateGlyphLayout(final String displayText) {
        return new GlyphLayout(font, displayText, font.getColor(), getParent().getContainerWidth(), Align.left, true);
    }
}
