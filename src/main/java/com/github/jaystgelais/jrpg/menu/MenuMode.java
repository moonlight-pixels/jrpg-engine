package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.ui.Layout;

import java.util.Map;
import java.util.Stack;

public final class MenuMode extends GameMode {
    private final Stack<Menu> menus = new Stack<>();
    private final MenuDefinition initialMenu;

    public MenuMode(final MenuDefinition initialMenu) {
        this.initialMenu = initialMenu;
    }

    @Override
    public final String getKey() {
        return "menu";
    }

    @Override
    public void onEnter(Map<String, Object> params) {
        menus.push(initialMenu.getMenu(getGame().getGraphicsService()));
    }

    @Override
    public void render(GraphicsService graphicsService) {
        if (!menus.isEmpty()) {
            graphicsService.renderStart();
            menus.peek().render(graphicsService);
            graphicsService.renderEnd();
        }
    }

    @Override
    public void update(long elapsedTime) {
        if (menus.isEmpty()) {
            exitGameMode();
        } else if (!menus.peek().isActive()) {
            menus.pop();
        } else {
            menus.peek().update(elapsedTime);
        }
    }

    @Override
    public void handleInput(InputService inputService) {
        if (!menus.isEmpty()) {
            menus.peek().handleInput(inputService);
        }
    }
}
