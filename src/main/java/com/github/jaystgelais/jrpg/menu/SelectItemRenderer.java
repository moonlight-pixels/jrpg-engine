package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.ui.LegacyContainer;
import com.github.jaystgelais.jrpg.ui.Content;

public interface SelectItemRenderer {
    Content renderItem(GraphicsService graphicsService, LegacyContainer legacyContainer);

    int getItemHeight(GraphicsService graphicsService);
}
