package com.github.jaystgelais.jrpg.demo;

import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.menu.Menu;
import com.github.jaystgelais.jrpg.menu.MenuDefinition;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.Layout;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelData;
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

                final Container locationContainer = layout.getContainer("location");
                final Panel locationPanel = new Panel(
                        new PanelData(locationContainer.getContentWidth(), locationContainer.getContentHeight())
                                .setPositionX(locationContainer.getContentPositionX())
                                .setPositionY(locationContainer.getContentPositionY())
                                .setTransitionTimeMs(0)
                );
                locationPanel.getPanelContainer().setContent(new Label(
                        locationPanel.getPanelContainer(),
                        graphicsService.getFontSet(),
                        GameState.getLocationDescription()
                ));
                locationContainer.setContent(locationPanel);

                final Container partyContainer = layout.getContainer("party");
                partyContainer.setContent(new Panel(
                        new PanelData(partyContainer.getContentWidth(), partyContainer.getContentHeight())
                                .setPositionX(partyContainer.getContentPositionX())
                                .setPositionY(partyContainer.getContentPositionY())
                                .setTransitionTimeMs(0)
                ));

                final Container menuContainer = layout.getContainer("menu");
                menuContainer.setContent(new Panel(
                        new PanelData(menuContainer.getContentWidth(), menuContainer.getContentHeight())
                                .setPositionX(menuContainer.getContentPositionX())
                                .setPositionY(menuContainer.getContentPositionY())
                                .setTransitionTimeMs(0)
                ));

                final Container statsContainer = layout.getContainer("stats");
                statsContainer.setContent(new Panel(
                        new PanelData(statsContainer.getContentWidth(), statsContainer.getContentHeight())
                                .setPositionX(statsContainer.getContentPositionX())
                                .setPositionY(statsContainer.getContentPositionY())
                                .setTransitionTimeMs(0)
                ));

                return layout;
            }

            @Override
            protected InputHandler getActiveInputHandler() {
                return null;
            }
        };
    }
}
