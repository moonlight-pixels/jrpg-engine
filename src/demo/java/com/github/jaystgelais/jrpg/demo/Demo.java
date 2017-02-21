package com.github.jaystgelais.jrpg.demo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.GraphicsServiceImpl;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.input.KeyboardInputService;
import com.github.jaystgelais.jrpg.state.StackedStateMachine;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelPalette;
import com.github.jaystgelais.jrpg.ui.panel.PanelText;

import java.util.Collections;
import java.util.Map;

/**
 * Created by jgelais on 2/16/17.
 */
public final class Demo {

    public static void main(final String[] arg) {
        GdxNativesLoader.load();
        initGame().start();
    }

    private static AssetManager initAssetManager() {
        return new AssetManager();
    }

    private static Game initGame() {
        final GraphicsService graphicsService = new GraphicsServiceImpl(initAssetManager());
        graphicsService.setResolutionWidth(640);
        graphicsService.setResolutionHeight(480);
        final InputService inputService = new KeyboardInputService();

        State panelState = new State() {
            private PanelText panelText;
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
                panel = new Panel(new PanelPalette(), graphicsService.getResolutionWidth() / 2, graphicsService.getResolutionHeight() / 2);
                panel.setPositionX(graphicsService.getResolutionWidth() / 4);
                panel.setPositionY(graphicsService.getResolutionHeight() / 2);
                String displayText = "Different platforms have different input facilities. On the desktop users " +
                        "can talk to your application via the keyboard and a mouse. The same is true for browser " +
                        "based games. On [GREEN]Android[], the mouse is replaced with a (capacitive) touch screen, and a " +
                        "hardware keyboard is often missing. All (compatible) Android devices also feature an " +
                        "accelerometer and sometimes even a compass (magnetic field sensor).";
                panelText = new PanelText(panel, graphicsService.getDefaultFont(), displayText);
                panel.add(panelText);
            }

            @Override
            public void onExit() {

            }

            @Override
            public void render(final GraphicsService graphicsService) {
                panel.render(graphicsService);
            }

            @Override
            public void update(final long elapsedTime) {
                if (inputService.isPressed(Inputs.OK)) {
                    panelText.updatePage();
                }
            }
        };
        StackedStateMachine gameModes = new StackedStateMachine(Collections.singleton(panelState), panelState);
        return new Game(gameModes, graphicsService);
    }

    private Demo() { }
}
