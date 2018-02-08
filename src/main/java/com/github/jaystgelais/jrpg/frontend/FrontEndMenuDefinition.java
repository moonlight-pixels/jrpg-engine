package com.github.jaystgelais.jrpg.frontend;

import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.menu.ExitGameAction;
import com.github.jaystgelais.jrpg.menu.Menu;
import com.github.jaystgelais.jrpg.menu.MenuDefinition;
import com.github.jaystgelais.jrpg.menu.SelectItem;
import com.github.jaystgelais.jrpg.menu.SelectList;
import com.github.jaystgelais.jrpg.ui.Layout;
import com.github.jaystgelais.jrpg.ui.panel.Panel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class FrontEndMenuDefinition extends MenuDefinition {
    private final NewGameLauncher newGameLauncher;

    public FrontEndMenuDefinition(final NewGameLauncher newGameLauncher) {
        this.newGameLauncher = newGameLauncher;
    }

    @Override
    public Menu getMenu(final GraphicsService graphicsService) {
        return new Menu() {
            private SelectList selectList;

            @Override
            protected InputHandler getActiveInputHandler() {
                return selectList;
            }

            @SuppressWarnings("checkstyle:magicnumber")
            @Override
            protected Layout createLayout() {
                Layout layout = new Layout(
                        graphicsService.getResolutionWidth() / 3,
                        graphicsService.getResolutionHeight() / 10,
                        graphicsService.getResolutionWidth() / 3,
                        graphicsService.getResolutionHeight() / 3
                );

                layout.splitVertical("margin", "content", 0);

                final Panel menuPanel = fillContainerWithPanel(layout.getContainer("content"));
                selectList = new SelectList(
                        menuPanel.getPanelLegacyContainer(),
                        Arrays.asList(
                                new SelectItem("New Game", () -> launchNewGame()),
                                new SelectItem("Load Saved Game", () -> System.out.println("")),
                                new SelectItem("Exit", () -> performAction(new ExitGameAction()))),
                        3
                );
                menuPanel.getPanelLegacyContainer().setContent(selectList);

                return layout;
            }
        };
    }

    private void launchNewGame() {
        newGameLauncher.initGameState();
        Map<String, Object> params = new HashMap<>();
        params.put("map", newGameLauncher.getInitialMap());
        params.put("location", newGameLauncher.getInitialLocation());
        Game.getInstance().activateGameMode("mapMode", params);
    }
}
