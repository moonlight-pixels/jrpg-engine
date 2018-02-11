package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.ui.MessagePopupPanel;

public final class MessageTriggerAction implements TriggerAction {

    private final MessagePopupPanel messagePopupPanel;

    public MessageTriggerAction(final String message) {
        this.messagePopupPanel = new MessagePopupPanel(message);
    }

    @Override
    public void update(final long elapsedTime) {
        messagePopupPanel.update(elapsedTime);
    }

    @Override
    public void handleInput(final InputService inputService) {
        messagePopupPanel.handleInput(inputService);
    }

    @Override
    public void render(final GraphicsService graphicsService) {

    }

    @Override
    public void startAction(final MapMode mapMode) {

    }

    @Override
    public boolean isComplete() {
        return messagePopupPanel.isComplete();
    }

    @Override
    public void dispose() {

    }
}
