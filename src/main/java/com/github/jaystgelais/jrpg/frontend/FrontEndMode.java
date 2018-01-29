package com.github.jaystgelais.jrpg.frontend;

import com.badlogic.gdx.graphics.Texture;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.menu.Menu;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class FrontEndMode extends GameMode {
    private final String titleScreenPath;
    private final NewGameLauncher newGameLauncher;
    private final StateMachine stateMachine;
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
        stateMachine.render(graphicsService);
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
                if (inputService.isPressed(Inputs.OK)) {
                    stateMachine.change("startMenu");
                }
            }
        });
        states.add(new StateAdapter() {
            private Menu startMenu;

            @Override
            public String getKey() {
                return "startMenu";
            }

            @Override
            public void update(final long elapsedTime) {
                if (!startMenu.isActive()) {
                    stateMachine.change("titleScreen");
                } else {
                    startMenu.update(elapsedTime);
                }
            }

            @Override
            public void handleInput(final InputService inputService) {
                startMenu.handleInput(inputService);
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                startMenu.render(graphicsService);
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                startMenu = new FrontEndMenuDefinition(newGameLauncher)
                        .getMenu(Game.getInstance().getGraphicsService());
            }
        });

        return new StateMachine(states, "titleScreen");
    }
}
