package com.github.jaystgelais.jrpg.demo;

import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.menu.Menu;
import com.github.jaystgelais.jrpg.menu.MenuDefinition;
import com.github.jaystgelais.jrpg.ui.Layout;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.text.Label;

public class GameMenuDefinition extends MenuDefinition {
    @Override
    public Menu getMenu(final GraphicsService graphicsService) {
        return new Menu() {
            @Override
            protected Layout getLayout() {
                Layout layout = new Layout(0, 0, graphicsService.getResolutionWidth(), graphicsService.getResolutionHeight());
                layout.splitVertical("location", "remainder", 0.1f);
                layout.splitHorizontal("remainder", "party", "remainder", 0.7f);
                layout.splitVertical("remainder", "menu", "stats", 0.7f);

                final Panel locationPanel = fillContainerWithPanel(layout.getContainer("location"));
                locationPanel.getPanelContainer().setContent(new Label(
                        locationPanel.getPanelContainer(),
                        graphicsService.getFontSet().getTextFont(),
                        GameState.getLocationDescription()
                ));

                fillContainerWithPanel(layout.getContainer("party"));
                fillContainerWithPanel(layout.getContainer("menu"));
                fillContainerWithPanel(layout.getContainer("stats"));

                return layout;
            }

            @Override
            protected InputHandler getActiveInputHandler() {
                return null;
            }
        };
    }
}
