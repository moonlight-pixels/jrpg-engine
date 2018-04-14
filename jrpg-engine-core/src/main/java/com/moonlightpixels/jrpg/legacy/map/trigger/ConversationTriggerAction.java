package com.moonlightpixels.jrpg.legacy.map.trigger;

import com.moonlightpixels.jrpg.legacy.conversation.Conversation;
import com.moonlightpixels.jrpg.legacy.conversation.ui.ConversationDisplay;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.InputService;
import com.moonlightpixels.jrpg.legacy.map.MapMode;

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
