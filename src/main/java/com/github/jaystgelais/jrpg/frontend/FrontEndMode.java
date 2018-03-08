package com.github.jaystgelais.jrpg.frontend;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.ui.Panel;
import com.github.jaystgelais.jrpg.ui.SelectItem;
import com.github.jaystgelais.jrpg.ui.SelectList;
import com.github.jaystgelais.jrpg.ui.UiStyle;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class FrontEndMode extends GameMode {
    private static final float DEFAULT_PANEL_BOTTOM_MARGIN_AS_PORTION_OF_SCREEN = 8;
    private static final float DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN = 4;
    private static final float DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN = 5;

    private final String titleScreenPath;
    private final NewGameLauncher newGameLauncher;
    private final StateMachine stateMachine;
    private DelayedInput okInput = new DelayedInput(Inputs.OK);
    private DelayedInput cancelInput = new DelayedInput(Inputs.CANCEL);
    private Texture titleScreen;


    @Inject
    public FrontEndMode(final String titleScreenPath, final NewGameLauncher newGameLauncher) {
        this.titleScreenPath = titleScreenPath;
        this.newGameLauncher = newGameLauncher;
        stateMachine = initStateMachine();
    }

    @Override
    public String getKey() {
        return "frontEnd";
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
        if (titleScreen == null) {
            if (!graphicsService.getAssetManager().isLoaded(titleScreenPath, Texture.class)) {
                graphicsService.getAssetManager().load(titleScreenPath, Texture.class);
                graphicsService.getAssetManager().finishLoading();
            }
            titleScreen = graphicsService.getAssetManager().get(titleScreenPath, Texture.class);
        }
        graphicsService.renderStart();
        graphicsService.drawSprite(titleScreen, 0, 0);
        graphicsService.renderEnd();
    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "titleScreen";
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (okInput.isPressed(inputService)) {
                    stateMachine.change("startMenu");
                }
            }
        });
        states.add(new StateAdapter() {
            private SelectList startMenu;

            @Override
            public String getKey() {
                return "startMenu";
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (cancelInput.isPressed(inputService)) {
                    stateMachine.change("titleScreen");
                } else {
                    startMenu.handleInput(inputService);
                }
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                startMenu = new SelectList(Arrays.asList(
                        createMenuItem("New Game", () -> launchNewGame()),
                        createMenuItem("Exit Game", () -> Game.getInstance().exitGame())
                ));
                Game.getInstance().getUserInterface().add(createPanel(startMenu).left());
            }

            @Override
            public void onExit() {
                Game.getInstance().getUserInterface().clear();
            }

            private <T extends Actor>  Panel<T> createPanel(final T content) {
                final Panel<T> panel = new Panel<>(content, UiStyle.get(Panel.PanelStyle.class));
                panel.setBounds(getDefaultX(), getDefaultY(), getDefaultWidth(), getDefaultHeight());
                return panel;
            }
        });

        return new StateMachine(states, "titleScreen");
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
        return graphicsService.getResolutionHeight() / DEFAULT_PANEL_BOTTOM_MARGIN_AS_PORTION_OF_SCREEN;
    }

    private void launchNewGame() {
        newGameLauncher.initGameState();
        Map<String, Object> params = new HashMap<>();
        params.put("map", newGameLauncher.getInitialMap());
        params.put("location", newGameLauncher.getInitialLocation());
        Game.getInstance().activateGameMode("mapMode", params);
    }

    private static SelectItem<Label> createMenuItem(final String text, final SelectItem.Action action) {
        final BitmapFont font = Game.getInstance().getGraphicsService().getFontSet().getTextFont();
        final Label.LabelStyle style = new Label.LabelStyle(font, font.getColor());
        final Label label = new Label(text, style);

        return new SelectItem<Label>(label, action);
    }
}
