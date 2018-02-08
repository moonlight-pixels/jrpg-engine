package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.conversation.Conversation;
import com.github.jaystgelais.jrpg.conversation.ui.ConversationDisplay;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.MapMode;

public final class ConversationTriggerAction implements TriggerAction {

    private final ConversationDisplay conversationDisplay;

    public ConversationTriggerAction(final ConversationDisplay conversationDisplay) {
        this.conversationDisplay = conversationDisplay;
    }

    public ConversationTriggerAction(final Conversation conversation) {
        this(new ConversationDisplay(conversation));
    }

    @Override
    public void update(final long elapsedTime) {
        conversationDisplay.update(elapsedTime);
    }

    @Override
    public void handleInput(final InputService inputService) {
        conversationDisplay.handleInput(inputService);
    }

    @Override
    public void render(final GraphicsService graphicsService) {

    }

    @Override
    public void startAction(final MapMode mapMode) {

    }

    @Override
    public boolean isComplete() {
        return conversationDisplay.isComplete();
    }

    @Override
    public void dispose() {

    }
}
