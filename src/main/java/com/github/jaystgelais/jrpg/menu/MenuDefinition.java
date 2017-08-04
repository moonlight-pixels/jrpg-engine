package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.panel.Panel;
import com.github.jaystgelais.jrpg.ui.panel.PanelData;

public abstract class MenuDefinition {
    public abstract Menu getMenu(GraphicsService graphicsService);

    protected final Panel fillContainerWithPanel(final Container container) {
        final Panel panel = new Panel(
                new PanelData(container.getContentWidth(), container.getContentHeight())
                        .setPositionX(container.getContentPositionX())
                        .setPositionY(container.getContentPositionY())
                        .setTransitionTimeMs(0)
        );
        container.setContent(panel);

        return panel;
    }
}
