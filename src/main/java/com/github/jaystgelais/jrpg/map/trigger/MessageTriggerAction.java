package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelData;
import com.github.jaystgelais.jrpg.ui.text.TextArea;
import com.github.jaystgelais.jrpg.ui.text.transition.TypedTextTransition;

import java.util.Collections;
import java.util.Map;

public final class MessageTriggerAction implements TriggerAction {
    private static final int DEFAULT_PANEL_TOP_MARGIN = 10;
    private static final int DEFAULT_TRANSITION_TIME_MS = 400;
    private static final int DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN = 2;
    private static final int DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN = 4;

    private final String message;
    private final GraphicsService graphicsService;
    private final int panelWidth;
    private final int panelHeight;
    private final StateMachine stateMachine;
    private boolean isComplete = false;

    public MessageTriggerAction(final String message, final GraphicsService graphicsService) {
        this(
                message,
                graphicsService,
                graphicsService.getResolutionWidth() / DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN,
                graphicsService.getResolutionHeight() / DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN
        );
    }

    public MessageTriggerAction(final String message, final GraphicsService graphicsService,
                                   final int panelWidth, final int panelHeight) {
        this.message = message;
        this.graphicsService = graphicsService;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        stateMachine = createStateMachine();
    }



    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    @Override
    public void startAction(final MapMode mapMode) {

    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void dispose() {
        stateMachine.dispose();
    }

    private StateMachine createStateMachine() {
        return new StateMachine(Collections.singleton(new StateAdapter() {
            private TextArea content;
            private Panel panel;

            @Override
            public String getKey() {
                return "panelState";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                panel = new Panel(
                        new PanelData(panelWidth, panelHeight)
                                .setPositionX(calculatePanelPositionX())
                                .setPositionY(calculatePanelPositionY())
                                .setTransitionTimeMs(DEFAULT_TRANSITION_TIME_MS)
                );
                content = new TextArea(
                        panel.getPanelContainer(),
                        graphicsService.getFontSet(),
                        message,
                        new TypedTextTransition()
                );
                panel.getPanelContainer().setContent(content);
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                graphicsService.renderStart();
                panel.render(graphicsService);
                graphicsService.renderEnd();
            }

            @Override
            public void dispose() {
                panel.dispose();
            }

            @Override
            public void handleInput(final InputService inputService) {
                panel.handleInput(inputService);
            }

            @Override
            public void update(final long elapsedTime) {
                panel.update(elapsedTime);
                if (content.isEmpty()) {
                    panel.close();
                }
                if (!panel.isActive()) {
                    isComplete = true;
                }
            }
        }), "panelState");
    }

    private int calculatePanelPositionY() {
        int cameraBottomEdge = Math.round(
                graphicsService.getCamera().position.y - (graphicsService.getResolutionHeight() / 2f)
        );
        int screenPositionY = graphicsService.getResolutionHeight() - panelHeight - DEFAULT_PANEL_TOP_MARGIN;
        return cameraBottomEdge + screenPositionY;
    }

    private int calculatePanelPositionX() {
        int cameraLeftEdge = Math.round(
                graphicsService.getCamera().position.x - (graphicsService.getResolutionWidth() / 2f)
        );
        int screenPositionX = Math.round((graphicsService.getResolutionWidth() - panelWidth) / 2f);
        return cameraLeftEdge + screenPositionX;
    }
}
