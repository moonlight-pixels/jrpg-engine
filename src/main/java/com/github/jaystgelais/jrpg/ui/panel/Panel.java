package com.github.jaystgelais.jrpg.ui.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jgelais on 2/15/17.
 */
public final class Panel implements Renderable, PanelContainer, InputHandler, Updatable {
    public static final int BORDER_THICKNESS = 3;
    public static final int CORNER_HEIGHT = 3;
    public static final int CORNER_WIDTH = 3;
    public static final int CONTENT_OFFSET_X = 8;
    public static final int CONTENT_OFFSET_Y = 8;
    public static final int MINIMUM_PANEL_HEIGHT = 8;

    private final PanelData data;
    private final Pixmap pixmap;
    private final StateMachine stateMachine;
    private PanelContent content;
    private boolean active = true;

    public Panel(final PanelData panelData) {
        this.data = panelData;
        pixmap = new Pixmap(panelData.getWidth(), panelData.getHeight(), Pixmap.Format.RGBA8888);
        stateMachine = initStateMachine();
    }

    public void setContent(final PanelContent content) {
        this.content = content;
        this.content.setParent(this);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    private void renderPanel(final GraphicsService graphicsService, final int currentHeight,
                             final boolean renderContent) {
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        drawTileFromArray(
                getBottomLeftCorner(), CORNER_WIDTH, CORNER_HEIGHT, 0, 0
        );
        drawTileFromArray(
                getBottomRightCorner(), CORNER_WIDTH, CORNER_HEIGHT, data.getWidth() - (CORNER_WIDTH + 1), 0
        );
        drawTileFromArray(
                getTopLeftCorner(), CORNER_WIDTH, CORNER_HEIGHT, 0, currentHeight - (CORNER_HEIGHT + 1)
        );
        drawTileFromArray(
                getTopRightCorner(), CORNER_WIDTH, CORNER_HEIGHT,
                data.getWidth() - (CORNER_WIDTH + 1),
                currentHeight - (CORNER_HEIGHT + 1)
        );

        Color[] topBorder = getTopBorder();
        Color[] bottomBorder = getBottomBorder();
        for (int x = BORDER_THICKNESS; x < data.getWidth() - (BORDER_THICKNESS + 1); x++) {
            drawTileFromArray(topBorder,    1, BORDER_THICKNESS, x, 0);
            drawTileFromArray(bottomBorder, 1, BORDER_THICKNESS, x, currentHeight - (BORDER_THICKNESS + 1));
        }

        Color[] leftBorder = getLeftBorder();
        Color[] rightBorder = getRightBorder();
        for (int y = BORDER_THICKNESS; y < currentHeight - (BORDER_THICKNESS + 1); y++) {
            drawTileFromArray(leftBorder, BORDER_THICKNESS, 1, 0,         y);
            drawTileFromArray(rightBorder, BORDER_THICKNESS, 1, data.getWidth() - (BORDER_THICKNESS + 1), y);
        }

        pixmap.setColor(data.getPalette().getBgPrimary());
        pixmap.fillRectangle(
                BORDER_THICKNESS, BORDER_THICKNESS,
                data.getWidth() - (BORDER_THICKNESS * 2), currentHeight - (BORDER_THICKNESS * 2)
        );

        graphicsService.drawSprite(pixmap, data.getPositionX(), data.getPositionY());
        if (renderContent) {
            content.render(graphicsService);
        }
    }

    private void drawTileFromArray(final Color[] tile,
                                   final int width, final int height,
                                   final int posX, final int posY) {
        int colorIndex = 0;
        for (int y = posY; y < posY + height; y++) {
            for (int x = posX; x < posX + width; x++) {
                Color color = tile[colorIndex++];
                if (color != null) {
                    pixmap.drawPixel(x, y, color.toIntBits());
                }
            }
        }
    }

    private Color[] getTopLeftCorner() {
        return new Color[]{
                getBorderEdgeMediumColor(), getBorderPrimaryColor(),    getBorderEdgeDarkColor(),
                null,                       getBorderEdgeMediumColor(), getBorderPrimaryColor(),
                null,                       null,                       getBorderEdgeDarkColor()
        };
    }

    private Color[] getTopBorder() {
        return new Color[]{
                getBorderEdgeLightColor(),
                getBorderPrimaryColor(),
                getBorderEdgeDarkColor()
        };
    }

    private Color[] getTopRightCorner() {
        return new Color[]{
                getBorderPrimaryColor(),    getBorderEdgeMediumColor(), getBorderEdgeDarkColor(),
                getBorderEdgeLightColor(),  getBorderEdgeDarkColor(),   null,
                getBorderEdgeDarkColor(),   null,                       null
        };
    }

    private Color[] getLeftBorder() {
        return new Color[]{
                getBorderEdgeMediumColor(), getBorderPrimaryColor(), getBorderEdgeDarkColor()
        };
    }

    private Color[] getRightBorder() {
        return new Color[]{
                getBorderEdgeMediumColor(), getBorderPrimaryColor(), getBorderEdgeDarkColor()
        };
    }

    private Color[] getBottomLeftCorner() {
        return new Color[]{
                null,                       null,                       getBorderEdgeLightColor(),
                null,                       getBorderEdgeMediumColor(), getBorderPrimaryColor(),
                getBorderEdgeMediumColor(), getBorderPrimaryColor(),    getBorderEdgeMediumColor()
        };
    }

    private Color[] getBottomBorder() {
        return new Color[]{
                getBorderEdgeLightColor(),
                getBorderPrimaryColor(),
                getBorderEdgeDarkColor()
        };
    }

    private Color[] getBottomRightCorner() {
        return new Color[]{
                getBorderEdgeDarkColor(),   null,                       null,
                getBorderEdgeMediumColor(), getBorderEdgeDarkColor(),   null,
                getBorderPrimaryColor(),    getBorderEdgeMediumColor(), getBorderEdgeDarkColor()
        };
    }

    public PanelData getData() {
        return data;
    }

    @Override
    public void dispose() {
        pixmap.dispose();
    }

    @Override
    public float getContainerPositionX() {
        return data.getPositionX() + CONTENT_OFFSET_X;
    }

    @Override
    public float getContainerPositionY() {
        return data.getPositionY() + CONTENT_OFFSET_Y;
    }

    @Override
    public float getContainerWidth() {
        return data.getWidth() - (2 * CONTENT_OFFSET_X);
    }

    @Override
    public float getContainerHeight() {
        return data.getHeight() - (2 * CONTENT_OFFSET_Y);
    }

    @Override
    public void close() {
        stateMachine.change("closing");
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    private Color getBorderEdgeDarkColor() {
        return data.getPalette().getBorderEdgeDark();
    }

    private Color getBorderPrimaryColor() {
        return data.getPalette().getBorderPrimary();
    }

    private Color getBorderEdgeMediumColor() {
        return data.getPalette().getBorderEdgeMedium();
    }

    private Color getBorderEdgeLightColor() {
        return data.getPalette().getBorderEdgeLight();
    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            private long timeInTransitionMs;

            @Override
            public String getKey() {
                return "opening";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInTransitionMs = 0L;
                if (timeInTransitionMs >= data.getTransitionTimeMs()) {
                    stateMachine.change("display");
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                float percentComplete = (float) timeInTransitionMs / (float) data.getTransitionTimeMs();
                int currentHeight = Math.max(MINIMUM_PANEL_HEIGHT, Math.round(percentComplete * data.getHeight()));
                renderPanel(graphicsService, currentHeight, false);
            }

            @Override
            public void update(final long elapsedTime) {
                timeInTransitionMs += elapsedTime;
                if (timeInTransitionMs > data.getTransitionTimeMs()) {
                    stateMachine.change("display");
                }
            }
        });
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "display";
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                renderPanel(graphicsService, data.getHeight(), true);
            }

            @Override
            public void handleInput(final InputService inputService) {
                content.handleInput(inputService);
            }
        });
        states.add(new StateAdapter() {
            private long timeLeftInTransitionMs;

            @Override
            public String getKey() {
                return "closing";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeLeftInTransitionMs = data.getTransitionTimeMs();
                if (timeLeftInTransitionMs <= 0) {
                    setActive(false);
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                if (isActive()) {
                    float percentRemaining = (float) timeLeftInTransitionMs / (float) data.getTransitionTimeMs();
                    int currentHeight = Math.max(MINIMUM_PANEL_HEIGHT, Math.round(percentRemaining * data.getHeight()));
                    renderPanel(graphicsService, currentHeight, false);
                }
            }

            @Override
            public void update(final long elapsedTime) {
                timeLeftInTransitionMs -= elapsedTime;
                if (timeLeftInTransitionMs < 0) {
                    setActive(false);
                }
            }
        });
        return new StateMachine(states, "opening");
    }

    @Override
    public void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }
}
