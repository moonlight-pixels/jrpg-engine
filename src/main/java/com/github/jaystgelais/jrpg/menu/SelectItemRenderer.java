package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.Content;

public interface SelectItemRenderer {
    Content renderItem(GraphicsService graphicsService, Container container);

    int getItemHeight(GraphicsService graphicsService);
}
