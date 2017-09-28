package com.github.jaystgelais.jrpg.conversation.ui;

import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.conversation.Conversation;
import com.github.jaystgelais.jrpg.conversation.ConversationNode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelData;

public final class ConversationDisplay implements Renderable, Updatable, InputHandler {
    private static final int DEFAULT_PANEL_TOP_MARGIN = 10;
    private static final int DEFAULT_TRANSITION_TIME_MS = 400;
    private static final int DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN = 2;
    private static final int DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN = 4;

    private final Conversation conversation;
    private final PanelData panelData;
    private final GraphicsService graphicsService;
    private Panel activePanel;
    private SimpleConversationLayout conversationLayout;

    public ConversationDisplay(final Conversation conversation, final PanelData panelData,
                               final GraphicsService graphicsService) {
        this.conversation = conversation;
        this.panelData = panelData;
        this.graphicsService = graphicsService;
        layoutNextNode();
    }

    public ConversationDisplay(final Conversation conversation, final PanelData panelData) {
        this(conversation, panelData, Game.getInstance().getGraphicsService());
    }

    public ConversationDisplay(final Conversation conversation) {
        this(conversation, getDefaultPanelData(Game.getInstance().getGraphicsService()));
    }

    public ConversationDisplay(final Conversation conversation, final GraphicsService graphicsService) {
        this(conversation, getDefaultPanelData(graphicsService), graphicsService);
    }

    private void layoutNextNode() {
        ConversationNode node = conversation.getNextNode();
        if (node == null) {
            activePanel = null;
        } else {
            activePanel = new Panel(panelData);
            conversationLayout = new SimpleConversationLayout(activePanel.getPanelContainer(), node, graphicsService);
            activePanel.getPanelContainer().setContent(conversationLayout);
        }
    }

    public boolean isComplete() {
        return activePanel == null;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        if (activePanel != null) {
            activePanel.render(graphicsService);
        }
    }

    @Override
    public void dispose() {
        if (activePanel != null) {
            activePanel.dispose();
        }
    }

    @Override
    public void update(final long elapsedTime) {
        if (activePanel != null) {
            activePanel.update(elapsedTime);

            if (conversationLayout.isComplete()) {
                activePanel.close();
            }

            if (!activePanel.isActive()) {
                activePanel.dispose();
                layoutNextNode();
            }
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (activePanel != null) {
            activePanel.handleInput(inputService);
        }
    }

    private static PanelData getDefaultPanelData(final GraphicsService graphicsService) {
        final int width = graphicsService.getResolutionWidth() / DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN;
        final int height = graphicsService.getResolutionHeight() / DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN;
        return new PanelData(width, height)
                .setPositionX(Math.round((graphicsService.getResolutionWidth() - width) / 2f))
                .setPositionY(graphicsService.getResolutionHeight() - height - DEFAULT_PANEL_TOP_MARGIN)
                .setTransitionTimeMs(DEFAULT_TRANSITION_TIME_MS);
    }
}
