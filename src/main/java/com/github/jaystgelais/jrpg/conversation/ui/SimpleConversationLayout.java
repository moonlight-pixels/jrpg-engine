package com.github.jaystgelais.jrpg.conversation.ui;

import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.conversation.ConversationNode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.AbstractContent;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.Layout;
import com.github.jaystgelais.jrpg.ui.text.Label;
import com.github.jaystgelais.jrpg.ui.text.TextArea;
import com.github.jaystgelais.jrpg.ui.text.transition.TypedTextTransition;

public final class SimpleConversationLayout extends AbstractContent {

    public static final float SPEAKER_LINE_HEIGHT_EM = 1.5f;
    private final Layout layout;
    private final TextArea textArea;

    public SimpleConversationLayout(final Container parent, final ConversationNode node,
                                    final GraphicsService graphicsService) {
        super(
                parent.getContentPositionX(),
                parent.getContentPositionY(),
                parent.getContentWidth(),
                parent.getContentHeight()
        );

        final int lineHeight = Math.round(graphicsService.getFontSet().getTextFont().getLineHeight());

        layout = new Layout(
                getScreenPositionX(),
                getScreenPositionY(),
                getWidth(),
                getHeight()
        );
        layout.splitVertical("speaker", "dialog", Math.round(lineHeight * SPEAKER_LINE_HEIGHT_EM));

        Container speakerContaner = layout.getContainer("speaker");
        speakerContaner.setContent(
                new Label(
                        speakerContaner,
                        graphicsService.getFontSet().getTextFont(),
                        String.format("%s:", node.getSpeaker().getName().toUpperCase()),
                        Align.topLeft
                )
        );

        Container dialogContainer = layout.getContainer("dialog");
        textArea = new TextArea(
                dialogContainer,
                graphicsService.getFontSet(),
                node.getLineTextList(),
                new TypedTextTransition()
        );
        dialogContainer.setContent(
                textArea
        );
    }

    public boolean isComplete() {
        return textArea.isEmpty();
    }

    @Override
    public void update(final long elapsedTime) {
        textArea.update(elapsedTime);
    }

    @Override
    public void handleInput(final InputService inputService) {
        textArea.handleInput(inputService);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        layout.render(graphicsService);
    }

    @Override
    protected boolean canChangeMargins() {
        return false;
    }

    @Override
    public void dispose() {
        layout.dispose();
    }
}
