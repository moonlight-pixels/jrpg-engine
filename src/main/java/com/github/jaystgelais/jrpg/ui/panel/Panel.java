package com.github.jaystgelais.jrpg.ui.panel;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.Content;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Panel implements Content {
    private static final int BORDER_THICKNESS = 3;
    private static final int MINIMUM_PANEL_WIDTH = 8;
    private static final int MINIMUM_PANEL_HEIGHT = 8;
    private static final int PANEL_CONTENT_MARGIN = 6;
    public static final float TEXTURE_OFFSET_X1 = 0.38f;
    public static final float TEXTURE_OFFSET_Y1 = 0.38f;
    public static final float TEXTURE_OFFSET_X2 = 0.61f;
    public static final float TEXTURE_OFFSET_Y2 = 0.61f;

    private final PanelData data;
    private final Pixmap pixmap;
    private final StateMachine stateMachine;
    private final Container panelContainer;
    private TextureRegion[][] panelSprites;
    private boolean active = true;

    public Panel(final PanelData panelData) {
        this.data = panelData;
        pixmap = new Pixmap(panelData.getWidth(), panelData.getHeight(), Pixmap.Format.RGBA8888);
        stateMachine = initStateMachine();
        panelContainer = new Container(getScreenPositionX(), getScreenPositionY(), getWidth(), getHeight());
        panelContainer.setRightMargin(PANEL_CONTENT_MARGIN);
        panelContainer.setLeftMargin(PANEL_CONTENT_MARGIN);
        panelContainer.setTopMargin(PANEL_CONTENT_MARGIN);
        panelContainer.setBottomMargin(PANEL_CONTENT_MARGIN);
    }

    public Container getPanelContainer() {
        return panelContainer;
    }

    public void close() {
        if (!stateMachine.getCurrentState().getKey().equals("closing")) {
            stateMachine.change("closing");
        }
    }

    private TextureRegion[][] getPanelSprites(final GraphicsService graphicsService) {
        if (panelSprites == null) {
            if (!graphicsService.getAssetManager().isLoaded("assets/jrpg/panel/gradient_panel.png", Texture.class)) {
                graphicsService.getAssetManager().load("assets/jrpg/panel/gradient_panel.png", Texture.class);
                graphicsService.getAssetManager().finishLoading();
            }
            panelSprites = TextureRegion.split(
                    graphicsService.getAssetManager().get("assets/jrpg/panel/gradient_panel.png", Texture.class),
                    BORDER_THICKNESS,
                    BORDER_THICKNESS
            );
        }

        return panelSprites;
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    private void renderPanel(final GraphicsService graphicsService, final int currentWidth, final int currentHeight) {
        TextureRegion[][] sprites = getPanelSprites(graphicsService);
        SpriteBatch spriteBatch = graphicsService.getSpriteBatch();
        int offsetX = graphicsService.getCameraOffsetX();
        int offsetY = graphicsService.getCameraOffsetY();

        sprites[1][1].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spriteBatch.draw(sprites[0][0], offsetX + getLeftX(currentWidth), offsetY + getTopY(currentHeight));
        spriteBatch.draw(
                sprites[0][1], offsetX + getCenterX(currentWidth), offsetY + getTopY(currentHeight),
                currentWidth - (2 * BORDER_THICKNESS), BORDER_THICKNESS
        );
        spriteBatch.draw(sprites[0][2], offsetX + getRightX(currentWidth), offsetY + getTopY(currentHeight));
        spriteBatch.draw(
                sprites[1][0], offsetX + getLeftX(currentWidth), offsetY + getCenterY(currentHeight),
                BORDER_THICKNESS, currentHeight - (2 * BORDER_THICKNESS)
        );
        spriteBatch.draw(
                sprites[1][1].getTexture(), offsetX + getCenterX(currentWidth), offsetY + getCenterY(currentHeight),
                currentWidth - (2 * BORDER_THICKNESS), currentHeight - (2 * BORDER_THICKNESS),
                TEXTURE_OFFSET_X1, TEXTURE_OFFSET_Y1, TEXTURE_OFFSET_X2, TEXTURE_OFFSET_Y2
        );
        spriteBatch.draw(
                sprites[1][2], offsetX + getRightX(currentWidth), offsetY + getCenterY(currentHeight),
                BORDER_THICKNESS, currentHeight - (2 * BORDER_THICKNESS)
        );
        spriteBatch.draw(sprites[2][0], offsetX + getLeftX(currentWidth), offsetY + getBottomY(currentHeight));
        spriteBatch.draw(
                sprites[2][1], offsetX + getCenterX(currentWidth), offsetY + getBottomY(currentHeight),
                currentWidth - (2 * BORDER_THICKNESS), BORDER_THICKNESS
        );
        spriteBatch.draw(sprites[2][2], offsetX + getRightX(currentWidth), offsetY + getBottomY(currentHeight));
    }

    private float getLeftX(final int currentWidth) {
        return data.getPositionX() + ((data.getWidth() - currentWidth) / 2);
    }

    private float getCenterX(final int currentWidth) {
        return data.getPositionX() + BORDER_THICKNESS + ((data.getWidth() - currentWidth) / 2);
    }

    private float getRightX(final int currentWidth) {
        return data.getPositionX() + currentWidth - BORDER_THICKNESS + ((data.getWidth() - currentWidth) / 2);
    }

    private float getTopY(final int currentHeight) {
        return data.getPositionY() + currentHeight - BORDER_THICKNESS + ((data.getHeight() - currentHeight) / 2);
    }

    private float getCenterY(final int currentHeight) {
        return data.getPositionY() + BORDER_THICKNESS + ((data.getHeight() - currentHeight) / 2);
    }

    private float getBottomY(final int currentHeight) {
        return data.getPositionY() + ((data.getHeight() - currentHeight) / 2);
    }

    @Override
    public void dispose() {
        pixmap.dispose();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
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
                int currentWidth = Math.max(MINIMUM_PANEL_WIDTH, Math.round(percentComplete * data.getWidth()));
                int currentHeight = Math.max(MINIMUM_PANEL_HEIGHT, Math.round(percentComplete * data.getHeight()));
                renderPanel(graphicsService, currentWidth, currentHeight);
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
                renderPanel(graphicsService, data.getWidth(), data.getHeight());
                panelContainer.render(graphicsService);
            }

            @Override
            public void update(final long elapsedTime) {
                panelContainer.update(elapsedTime);
            }

            @Override
            public void handleInput(final InputService inputService) {
                panelContainer.handleInput(inputService);
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
                    int currentWidth = Math.max(MINIMUM_PANEL_WIDTH, Math.round(percentRemaining * data.getWidth()));
                    int currentHeight = Math.max(MINIMUM_PANEL_HEIGHT, Math.round(percentRemaining * data.getHeight()));
                    renderPanel(graphicsService, currentWidth, currentHeight);
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


    @Override
    public int getWidth() {
        return data.getWidth();
    }

    @Override
    public int getHeight() {
        return data.getHeight();
    }

    @Override
    public int getScreenPositionX() {
        return data.getPositionX();
    }

    @Override
    public int getScreenPositionY() {
        return data.getPositionY();
    }

    @Override
    public int getLeftMargin() {
        return 0;
    }

    @Override
    public int getRightMargin() {
        return 0;
    }

    @Override
    public int getTopMargin() {
        return 0;
    }

    @Override
    public int getBottomMargin() {
        return 0;
    }

    @Override
    public void setLeftMargin(final int px) {

    }

    @Override
    public void setRightMargin(final int px) {

    }

    @Override
    public void setTopMargin(final int px) {

    }

    @Override
    public void setBottomMargin(final int px) {

    }

    @Override
    public void setLeftMargin(final float percent) {

    }

    @Override
    public void setRightMargin(final float percent) {

    }

    @Override
    public void setTopMargin(final float percent) {

    }

    @Override
    public void setBottomMargin(final float percent) {

    }
}
