package com.github.jaystgelais.jrpg.demo.ui.panel;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelPalette;
import com.github.jaystgelais.jrpg.ui.panel.PanelText;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PanelDemo extends GameMode {
    private final StateMachine stateMachine;

    public PanelDemo() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "waiting";
            }

            @Override
            public void handleInput(final InputService inputService) {
                if (inputService.isPressed(Inputs.OK)) {
                    stateMachine.change("panel");
                }
            }
        });

        states.add(new StateAdapter() {
            private Panel panel;
            @Override
            public void dispose() {
                panel.dispose();
            }

            @Override
            public String getKey() {
                return "panel";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                String displayText = "Different platforms have different input facilities. On the desktop users " +
                        "can talk to your application via the keyboard and a mouse. The same is true for browser " +
                        "based games. On [GREEN]Android[], the mouse is replaced with a (capacitive) touch screen, and a " +
                        "hardware keyboard is often missing. All (compatible) Android devices also feature an " +
                        "accelerometer and sometimes even a compass (magnetic field sensor).";

                panel = new Panel(
                        getGame().getGraphicsService().getResolutionWidth() / 2,
                        getGame().getGraphicsService().getResolutionHeight() / 4,
                        new PanelPalette()
                );

                panel.setContent(new PanelText(getGame().getGraphicsService().getDefaultFont(), displayText));
                panel.getData().setPositionX(getGame().getGraphicsService().getResolutionWidth() / 4);
                panel.getData().setPositionY(getGame().getGraphicsService().getResolutionHeight() / 2);
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                panel.render(graphicsService);
            }

            @Override
            public void update(final long elapsedTime) {
                panel.update(elapsedTime);
                if (!panel.isActive()) {
                    getGame().exitGame();
                }
            }

            @Override
            public void handleInput(final InputService inputService) {
                panel.handleInput(inputService);
            }
        });
        this.stateMachine = new StateMachine(states, "waiting");
    }

    @Override
    public void update(long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public void handleInput(InputService inputService) {
        stateMachine.handleInput(inputService);
    }

    @Override
    public void render(GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    @Override
    public void dispose() {
        stateMachine.dispose();
    }

    @Override
    public String getKey() {
        return "panelDemo";
    }
}
