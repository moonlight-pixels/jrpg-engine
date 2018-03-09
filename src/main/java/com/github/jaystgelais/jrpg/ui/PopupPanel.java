package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.tween.FloatTween;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class PopupPanel implements Updatable, InputHandler {
    private static final int DEFAULT_PANEL_TOP_MARGIN = 10;
    private static final int DEFAULT_TRANSITION_TIME_MS = 400;
    private static final float DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN = 2;
    private static final float DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN = 4;
    private static final float MINIMUM_PANEL_SCALE = 0.1f;

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final StateMachine stateMachine;
    private boolean complete;

    public PopupPanel(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        stateMachine = initStateMachine();
        complete = false;
    }

    public PopupPanel() {
        this(getDefaultX(), getDefaultY(), getDefaultWidth(), getDefaultHeight());
    }

    protected abstract Actor buildLayout();

    protected abstract Optional<Updatable> getUpdatable();

    protected abstract Optional<InputHandler> getInputHandler();

    protected abstract boolean isDisplayComplete();

    public final boolean isComplete() {
        return complete;
    }

    @Override
    public final void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public final void handleInput(final InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    private void renderScaledPanel(final float panelScale) {
        float currentWidth = panelScale * width;
        float currentHeight = panelScale * height;
        float currentX = x + ((width - currentWidth) / 2);
        float currentY = y + ((height - currentHeight) / 2);
        final Panel<Actor> panel = new Panel<>(UiStyle.get("popup", Panel.PanelStyle.class));
        panel.setBounds(currentX, currentY, currentWidth, currentHeight);

        Game.getInstance().getUserInterface().clear();
        Game.getInstance().getUserInterface().add(panel);
    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            private FloatTween tween;

            @Override
            public String getKey() {
                return "opening";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                tween = new FloatTween(MINIMUM_PANEL_SCALE, 1.0f, DEFAULT_TRANSITION_TIME_MS);
                if (tween.isComplete()) {
                    stateMachine.change("display");
                }
            }

            @Override
            public void update(final long elapsedTime) {
                tween.update(elapsedTime);
                if (tween.isComplete()) {
                    stateMachine.change("display");
                } else {
                    renderScaledPanel(tween.getValue());
                }
            }
        });
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "display";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                Game.getInstance().getUserInterface().add(createPanel(buildLayout()).bottom().left());
            }

            @Override
            public void onExit() {
                Game.getInstance().getUserInterface().clear();
            }

            @Override
            public void handleInput(final InputService inputService) {
                getInputHandler().ifPresent(inputHandler -> inputHandler.handleInput(inputService));
                if (isDisplayComplete()) {
                    stateMachine.change("closing");
                }
            }

            @Override
            public void update(final long elapsedTime) {
                getUpdatable().ifPresent(updatable -> updatable.update(elapsedTime));
                if (isDisplayComplete()) {
                    stateMachine.change("closing");
                }
            }

            private <T extends Actor>  Panel<T> createPanel(final T content) {
                final Panel<T> panel = new Panel<>(content, UiStyle.get("popup", Panel.PanelStyle.class));
                panel.setBounds(x, y, width, height);
                return panel;
            }
        });
        states.add(new StateAdapter() {
            private FloatTween tween;

            @Override
            public String getKey() {
                return "closing";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                tween = new FloatTween(1.0f, MINIMUM_PANEL_SCALE, DEFAULT_TRANSITION_TIME_MS);
                if (tween.isComplete()) {
                    complete = true;
                }
            }

            @Override
            public void update(final long elapsedTime) {
                tween.update(elapsedTime);
                if (tween.isComplete()) {
                    complete = true;
                    Game.getInstance().getUserInterface().clear();
                } else {
                    renderScaledPanel(tween.getValue());
                }
            }
        });
        return new StateMachine(states, "opening");
    }

    private static float getDefaultWidth() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return graphicsService.getResolutionWidth() / DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN;
    }

    private static float getDefaultHeight() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return graphicsService.getResolutionHeight() / DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN;
    }

    private static float getDefaultX() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return Math.round((graphicsService.getResolutionWidth() - getDefaultWidth()) / 2f);
    }

    private static float getDefaultY() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return graphicsService.getResolutionHeight() - getDefaultHeight() - DEFAULT_PANEL_TOP_MARGIN;
    }
}
