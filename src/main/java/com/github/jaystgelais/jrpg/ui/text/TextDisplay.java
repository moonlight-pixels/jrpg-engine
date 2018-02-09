package com.github.jaystgelais.jrpg.ui.text;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.ui.text.transition.TextTransition;
import com.github.jaystgelais.jrpg.ui.text.transition.TextTransitionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class TextDisplay extends Widget implements Updatable, InputHandler {

    private final TextTransition textTransition;
    private final List<String> rawPages;
    private final Queue<GlyphLayout> pages = new LinkedList<>();
    private final DelayedInput okInput = new DelayedInput(Inputs.OK);
    private final BitmapFont font;
    private TextTransitionHandler transitionHandler;
    private boolean isTransitionActive;
    private GlyphLayout currentPage;
    private GlyphLayout nextPage;
    private boolean hasLayoutBeenCalled = false;

    public TextDisplay(final BitmapFont font, final String text, final TextTransition textTransition) {
        this(font, Collections.singletonList(text), textTransition);
    }

    public TextDisplay(final BitmapFont font, final List<String> rawPages,
                       final TextTransition textTransition) {
        this.font = font;
        this.rawPages = rawPages;
        this.textTransition = textTransition;
    }

    public BitmapFont getFont() {
        return font;
    }

    public boolean isEmpty() {
        return hasLayoutBeenCalled && pages.isEmpty();
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        validate();

        if (isTransitionActive) {
            transitionHandler.draw(batch, getX(), getY(), getWidth(), getHeight());
        } else {
            font.draw(batch, currentPage, getX(), getY() + getHeight());
        }
    }

    @Override
    public void layout() {
        for (String rawPage : rawPages) {
            pages.addAll(paginateText(rawPage, getWidth(), getHeight()));
        }
        pages.add(new GlyphLayout());
        loadNextPage();
        hasLayoutBeenCalled = true;
    }

    @Override
    public float getPrefWidth() {
        if (currentPage != null) {
            return currentPage.width;
        }
        return font.getSpaceWidth();
    }

    @Override
    public float getPrefHeight() {
        if (currentPage != null) {
            return currentPage.height;
        }

        return font.getLineHeight();
    }

    @Override
    public void update(final long elapsedTime) {
        if (isTransitionActive) {
            transitionHandler.update(elapsedTime);
            if (transitionHandler.isComplete()) {
                completeTransition();
            }
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (isTransitionActive) {
            if (okInput.isPressed(inputService)) {
                completeTransition();
            }
        } else {
            if (!isEmpty() && okInput.isPressed(inputService)) {
                loadNextPage();
            }
        }
    }

    private void completeTransition() {
        currentPage = nextPage;
        nextPage = null;
        isTransitionActive = false;
    }

    private void loadNextPage() {
        nextPage = pages.remove();
        transitionHandler = textTransition.handleTransition(this, currentPage, nextPage);
        isTransitionActive = true;
    }

    private List<GlyphLayout> paginateText(final String text, final float width, final float height) {
        List<GlyphLayout> results = new LinkedList<>();

        Queue<String> words = new LinkedList<>();
        words.addAll(Arrays.asList(text.split(" ")));

        String pageText = null;
        GlyphLayout layout = generateGlyphLayout("", width);
        while (!words.isEmpty()) {
            pageText = (pageText == null) ? words.peek() : String.join(" ", pageText, words.peek());
            GlyphLayout nextLayout = generateGlyphLayout(pageText, width);
            if (nextLayout.height < height) {
                layout = nextLayout;
                words.remove();
            } else {
                results.add(layout);
                pageText = null;
                layout = generateGlyphLayout("", width);
            }
        }
        results.add(layout);

        return results;
    }

    private GlyphLayout generateGlyphLayout(final String text, final float width) {
        return new GlyphLayout(font, text, font.getColor(), width, Align.left, true);
    }
}
