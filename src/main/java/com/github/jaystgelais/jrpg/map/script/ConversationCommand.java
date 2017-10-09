package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.conversation.Conversation;
import com.github.jaystgelais.jrpg.conversation.ui.ConversationDisplay;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;

public final class ConversationCommand implements Command {
    private final Conversation conversation;
    private ConversationDisplay conversationDisplay;

    public ConversationCommand(final Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean isComplete() {
        return conversationDisplay.isComplete();
    }

    @Override
    public void start() {
        conversationDisplay = new ConversationDisplay(conversation);
    }

    @Override
    public void update(final long elapsedTime) {
        conversationDisplay.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        graphicsService.renderStart();
        conversationDisplay.render(graphicsService);
        graphicsService.renderEnd();
    }

    @Override
    public void dispose() {
        conversationDisplay.dispose();
    }

    @Override
    public void handleInput(final InputService inputService) {
        conversationDisplay.handleInput(inputService);
    }

    @Override
    public String toString() {
        return String.format("ConversationCommand{conversation=%s}", conversation);
    }
}
