package com.github.jaystgelais.jrpg.ui.panel;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public final class PanelText extends PanelContent {
    private final BitmapFont font;
    private Queue<String> words = new LinkedList<>();
    private GlyphLayout layout;

    public PanelText(final BitmapFont font, final String text) {
        this.font = font;
        words.addAll(Arrays.asList(text.split(" ")));
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        if (layout == null) {
            layout = generateGlyphLayout("");
            updatePage();
        }
        font.draw(
                graphicsService.getSpriteBatch(), layout,
                getAbsoluteX(), getAbsoluteY() + getParent().getContainerHeight()
        );
    }

    private boolean updatePage() {
        if (words.isEmpty()) {
            return false;
        }

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

        return true;
    }

    @Override
    public float getRelativeX() {
        return 0;
    }

    @Override
    public float getRelativeY() {
        return 0;
    }

    @Override
    public void dispose() {

    }

    private GlyphLayout generateGlyphLayout(final String displayText) {
        return new GlyphLayout(font, displayText, font.getColor(), getParent().getContainerWidth(), Align.left, true);
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (inputService.isPressed(Inputs.OK)) {
            boolean wasNextPageAvailable = updatePage();
            if (!wasNextPageAvailable) {
                getParent().close();
            }
        }
    }
}
