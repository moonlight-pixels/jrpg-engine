package com.github.jaystgelais.jrpg.ui.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.tween.IntegerTween;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.text.transition.TextTransition;
import com.github.jaystgelais.jrpg.ui.text.transition.TextTransitionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class TextArea extends AbstractContent implements Updatable, InputHandler {
    public static final long TIME_PER_GLYPH_MS = 50;

    private final TextTransition textTransition;
    private final Queue<GlyphLayout> pages = new LinkedList<>();
    private final StateMachine stateMachine = initStateMachine();
    private final BitmapFont font;
    private GlyphLayout currentPage;
    private GlyphLayout nextPage;

    public TextArea(final Container parent, final FontSet fontSet, final String text,
                    final TextTransition textTransition) {
        this(parent, fontSet, Collections.singletonList(text), textTransition);
    }

    public TextArea(final Container parent, final FontSet fontSet, final List<String> rawPages,
                    final TextTransition textTransition) {
        super(
                parent.getContentPositionX(), parent.getContentPositionY(),
                parent.getContentWidth(), parent.getContentHeight()
        );
        font = fontSet.getTextFont();
        this.textTransition = textTransition;
        for (String rawPage : rawPages) {
            pages.addAll(paginateText(fontSet, rawPage));
        }
        pages.add(new GlyphLayout());
    }

    public BitmapFont getFont() {
        return font;
    }

    public boolean isEmpty() {
        return pages.isEmpty();
    }

    private List<GlyphLayout> paginateText(final FontSet fontSet, final String text) {
        List<GlyphLayout> results = new LinkedList<>();

        Queue<String> words = new LinkedList<>();
        words.addAll(Arrays.asList(text.split(" ")));

        String pageText = null;
        GlyphLayout layout = generateGlyphLayout(fontSet, "");
        while (!words.isEmpty()) {
            pageText = (pageText == null) ? words.peek() : String.join(" ", pageText, words.peek());
            GlyphLayout nextLayout = generateGlyphLayout(fontSet, pageText);
            if (nextLayout.height < getHeight()) {
                layout = nextLayout;
                words.remove();
            } else {
                results.add(layout);
                pageText = null;
                layout = generateGlyphLayout(fontSet, "");
            }
        }
        results.add(layout);

        return results;
    }

    private GlyphLayout generateGlyphLayout(final FontSet fontSet, final String text) {
        return new GlyphLayout(
                fontSet.getTextFont(), text, fontSet.getTextFont().getColor(), getWidth(), Align.left, true
        );
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }

    @Override
    public void dispose() {
        stateMachine.dispose();
    }

    private StateMachine initStateMachine() {
        final TextArea textArea = this;

        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            private TextTransitionHandler handler;

            @Override
            public String getKey() {
                return "transition";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                nextPage = pages.remove();
                handler = textTransition.handleTransition(textArea, currentPage, nextPage);
            }

            @Override
            public void update(final long elapsedTime) {
                handler.update(elapsedTime);
                if (handler.isComplete()) {
                    stateMachine.change("waiting");
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                handler.render(graphicsService);
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (inputService.isPressed(Inputs.OK)) {
                    stateMachine.change("waiting");
                }
            }

            @Override
            public void onExit() {
                currentPage = nextPage;
                nextPage = null;
            }
        });
        states.add(new StateAdapter() {

            @Override
            public String getKey() {
                return "waiting";
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                font.draw(
                        graphicsService.getSpriteBatch(), currentPage,
                        getContentPositionX(), getContentPositionY() + getHeight()
                );
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (!isEmpty() && inputService.isPressed(Inputs.OK)) {
                    stateMachine.change("transition");
                }
            }
        });
        return new StateMachine(states, "transition");
    }
}
