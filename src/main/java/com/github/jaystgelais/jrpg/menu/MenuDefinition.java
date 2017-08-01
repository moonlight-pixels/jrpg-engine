package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;

public abstract class MenuDefinition {
    public abstract Menu getMenu(GraphicsService graphicsService);

    protected void setContent() {

    }
}
