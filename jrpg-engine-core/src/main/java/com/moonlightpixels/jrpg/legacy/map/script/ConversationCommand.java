package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.conversation.ConversationProvider;
import com.moonlightpixels.jrpg.legacy.conversation.ui.ConversationDisplay;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.input.InputService;

public final class ConversationCommand implements Command {
    private final ConversationProvider conversationProvider;
    private ConversationDisplay conversationDisplay;

    public ConversationCommand(final ConversationProvider conversationProvider) {
        this.conversationProvider = conversationProvider;
    }

    @Override
    public boolean isComplete() {
        return conversationDisplay.isComplete();
    }

    @Override
    public void start() {
        conversationDisplay = new ConversationDisplay(conversationProvider.getConversation());
    }

    @Override
    public void update(final long elapsedTime) {
        conversationDisplay.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput(final InputService inputService) {
        conversationDisplay.handleInput(inputService);
    }
}
