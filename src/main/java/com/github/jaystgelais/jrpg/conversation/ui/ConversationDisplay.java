package com.github.jaystgelais.jrpg.conversation.ui;

import com.github.jaystgelais.jrpg.conversation.Conversation;
import com.github.jaystgelais.jrpg.conversation.ConversationNode;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.Updatable;

public final class ConversationDisplay implements Updatable, InputHandler {
    private static final int DEFAULT_PANEL_TOP_MARGIN = 10;
    private static final int DEFAULT_TRANSITION_TIME_MS = 400;
    private static final float DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN = 2;
    private static final float DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN = 4;
    private static final float MINIMUM_PANEL_SCALE = 0.1f;

    private final Conversation conversation;
    private ConversationNodeDisplay currentNodeDisplay;
    private boolean complete;

    public ConversationDisplay(final Conversation conversation) {
        this.conversation = conversation;
        complete = false;
        renderNextNode();
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public void update(final long elapsedTime) {
        if (!complete) {
            currentNodeDisplay.update(elapsedTime);
            if (currentNodeDisplay.isComplete()) {
                renderNextNode();
            }
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (!complete) {
            currentNodeDisplay.handleInput(inputService);
            if (currentNodeDisplay.isComplete()) {
                renderNextNode();
            }
        }
    }

    private void renderNextNode() {
        final ConversationNode node = conversation.getNextNode();
        if (node == null) {
            complete = true;
        } else {
            currentNodeDisplay = new ConversationNodeDisplay(node);
        }
    }
}
