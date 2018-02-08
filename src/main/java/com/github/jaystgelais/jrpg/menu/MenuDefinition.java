package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.ui.LegacyContainer;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelData;

public abstract class MenuDefinition {
    public abstract Menu getMenu(GraphicsService graphicsService);

    protected final Panel fillContainerWithPanel(final LegacyContainer legacyContainer) {
        final Panel panel = new Panel(
                new PanelData(legacyContainer.getContentWidth(), legacyContainer.getContentHeight())
                        .setPositionX(legacyContainer.getContentPositionX())
                        .setPositionY(legacyContainer.getContentPositionY())
                        .setTransitionTimeMs(0)
        );
        legacyContainer.setContent(panel);

        return panel;
    }
}
