package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.conversation.Conversation;
import com.moonlightpixels.jrpg.conversation.ui.ConversationDisplay;
import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.input.InputService;
import com.moonlightpixels.jrpg.map.MapMode;

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
