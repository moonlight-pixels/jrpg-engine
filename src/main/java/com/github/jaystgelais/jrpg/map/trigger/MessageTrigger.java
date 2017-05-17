package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelData;
import com.github.jaystgelais.jrpg.ui.text.TextArea;

import java.util.Collections;
import java.util.Map;

public abstract class MessageTrigger implements Trigger {
    private static final int DEFAULT_PANEL_TOP_MARGIN = 10;
    private static final int DEFAULT_TRANSITION_TIME_MS = 400;

    private final String message;
    private final GraphicsService graphicsService;
    private final int panelWidth;
    private final int panelHeight;

    protected MessageTrigger(final String message, final GraphicsService graphicsService,
                             final int panelWidth, final int panelHeight) {
        this.message = message;
        this.graphicsService = graphicsService;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    @Override
    public final StateMachine performAction(final TriggerContext context) {
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
                content = new TextArea(panel.getPanelContainer(), graphicsService.getFontSet(), message);
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
                    context.done();
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
